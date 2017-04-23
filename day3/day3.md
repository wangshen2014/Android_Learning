# 数据库 #

new File("info.txt")
在android中是不能创建info.txt的
只有再用流的时候，才可以创建出文件。




- 数据库的创建
- 
谷歌工程师 给出了SQLiteOpenHelper 这个类来帮助创建数据库，定义一个类继承它就行

    public class MyOpenHelper extends SQLiteOpenHelper

下面new一个

	myOpenHelper = new MyOpenHelper(getApplicationContext());
上面并不能创建出数据库

    打开创建数据库，如果是第一次，则创建
    	SQLiteDatabase sqLiteDatabase = myOpenHelper.getWritableDatabase();
    打开创建数据库，如果是第一次，则创建   如果磁盘满了，则返回一个只读的
    	SQLiteDatabase readableDatabase = myOpenHelper.getReadableDatabase();
    



- 两个方法
- 
onCreate方法

当数据库第一次被创建的时候被创建，因此这个数据库特别适合做表结构的初始化

	public void onCreate(SQLiteDatabase db) 

	db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20),phone varchar(20))");

SQLiteDatabase db就是getWritableDatabase得到的，SQLite为了存储效率，所有的东西不区分数据类型，都是string类型。但是写的数据还是要按照标准的数据库语句写

onUpgrade方法

当数据库version变大的时候自动调用，适合做升级的事务。例如增加一张表，更新表

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("alter table info add phone varchar(20)");
		
	}

下面添加几个按钮，分别对应增添改查



- 使用sql语句进行增删改查
- 

	public void click1(View v){
		//[1]获取数据库
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		//[2]插入
		db.execSQL("insert into info(name,phone) values(?,?)", new Object[]{"zhangsan","1388888"});
		//[3]关闭
		db.close();
		
	}
	
	
	//删除
	public void click2(View v){
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		db.execSQL("delete from info where name=?", new Object[]{"zhangsan"});
		db.close();
	}
	
	//修改
	public void click3(View v){
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		db.execSQL("update info set phone=? where name=? ", new Object[]{"138888888","xxxx"});
		db.close();
	}
	
	
	//
	public void click4(View v){
		
		SQLiteDatabase db = myOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from info", null);
		if (cursor!= null&&cursor.getCount()>0) {
			while(cursor.moveToNext()){
				//columnIndex代表第几列的index，这个数据库有3列，第一列是id，自动会增加，不需要维护，只需要取出2,3列
				String name = cursor.getString(1);
				String phone = cursor.getString(2);
				System.out.println("name:"+name+"----"+phone);
			}
		}
	}


缺点：sql语句容易出错；没有返回值，无法判断状态



- 业务逻辑
- 
1.获取4个控件

在button上设置一个监听事件，注意匿名内部类的写法，注意大小写OnClickListener 以及 onClick。

	button.setOnClickListener(new View.OnClickListener(){
	@Override
	public void onClick(View v){
	}


2.点击button后，根据scenario编写相应的业务逻辑

为了可以存储用户的信息，定义一个类class Userinfo，设置saveInfo和getInfo方法

saveUserInfo中将信息写入文件
getInfo将用户名、密码，ischecked状态从文件中读出来，读的时候，file--》输入流--》流入流reader--》bufferReader

    File file = new File("/data/data/com.example.administrator.login/info.txt");
    FileInputStream fileInputStream = new FileInputStream(file);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
    String result = bufferedReader.readLine();

注意恢复信息时，checkbox的设置方法是：setChecked

    if ("Remember".equals(hashMap.get("isChecked"))){
    checkBox.setChecked(true);
    }


- 用谷歌封装好的api进行增删改查

1、增

    long insertReslut = db.insert(tableName，null，values);

因为values要求是一个 key：value的hashmap

	ContentValues contentValues = new ContentValues();
	contentValues.put("name","wangwu");
	contentValues.put("phone","15855514611");
	long result = db.insert("info",null,contentValues);
	Log.i(TAG,"insert " + result + " into table");


2、删

	int rowAffected = db.delete("info","name=?",new String[]{"wangwu"});
	Log.i(TAG,"delete " + rowAffected + " from table");

3.更新

	ContentValues contentValues = new ContentValues();
	contentValues.put("phone","14755773233");
	int rowAffected = db.update("info",contentValues,"name=?",new String[]{"wangwu"});
	Log.i(TAG,"update " + rowAffected + " from table");


4.查找
根据name 去查phone

	Cursor cursor = db.query("info",new String[]{"phone"},"name=?",name,null,null,null);
	if (cursor!= null&&cursor.getCount()>0) {
		while(cursor.moveToNext()){
//				String name = cursor.getString(1);
			String phone = cursor.getString(0);
			Log.i(TAG,"name:"+name[0]+"----"+phone);
		}
	}

优点：由api自己去构建sql语句，有返回值。
缺点：不够灵活，多表查询





# 事务 transaction  转账 #

事务就是执行一段逻辑，要么同时成功，要么同时失败。

	db.beginTransaction();

交易里的常见场景是，a给b打钱，其实这个就是将数据库的里a的钱减少，b的值增加。这样完成一个交易。

交易的整个过程以beginTransation()开始。
首先减少a的余额，然后增加b的余额

但是如果在执行完第一步后，出现了错误，那么此交易不成功。a的操作会回滚（将a的钱放回去）

