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


# 修复一些bug #

- 使用上下文获取文件保存的路径（取代使用硬编码的方式）
- 

What is Context in Android?

Putting it simply:
As the name suggests, it's the context of current state of the application/object. It lets newly-created objects understand what has been going on. Typically you call it to get information regarding another part of your program (activity and package/application).

You can get the context by invoking getApplicationContext(), getContext(), getBaseContext() or this (when in a class that extends from Context, such as the Application, Activity, Service and IntentService classes).

Typical uses of context:

1.Creating new objects: Creating new views, adapters, listeners:

TextView tv = new TextView(getContext());
ListAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), ...);

Accessing standard common resources: Services like LAYOUT_INFLATER_SERVICE,
 
2.SharedPreferences:

context.getSystemService(LAYOUT_INFLATER_SERVICE)

getApplicationContext().getSharedPreferences(*name*, *mode*);

3.Accessing components implicitly: Regarding content providers, broadcasts, intent

getApplicationContext().getContentResolver().query(uri, ...);



例如，我们直接可以通过Context来打开一个文件数据流，而你只要将文件名字和mode，填充就行了。不用care 这个文件存在哪里，或者构造一个文件输出流。

    FileOutputStream fileOutputStream = context.openFileOutput("info2.txt",0);

看下这个函数的说明

Open a private file associated with this Context's application package for writing. Creates the file if it doesn't already exist.

No additional permissions are required for the calling app to read or write the returned file.

Parameters
name	String: The name of the file to open; can not contain path separators.

mode	int: Operating mode. Use 0 or MODE_PRIVATE for the default operation. Use MODE_APPEND to append to an existing file.

这一句话其实就相当于：

    File file = new File("/data/data/com.example.administrator.login/info.txt");
    FileOutputStream fileOutputStream = new FileOutputStream(file);



同样的复杂的文件读取操作，也可以直接通过context完成输入流的获取：

    FileInputStream fileInputStream = context.openFileInput("info2.txt");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))

这两句话就可以代替下面的操作

    File file = new File("/data/data/com.example.administrator.login/info.txt");
    FileInputStream fileInputStream = new FileInputStream(file);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));


获取文件，可以将

    File file = new File("/data/data/com.example.administrator.login/info.txt");

替换为

    File file = new File(getApplicationContext().getFilesDir(),"info2.txt");



这是getFileDir的说明

getFilesDir


File getFilesDir ()

Returns the absolute path to the directory on the filesystem where files created with openFileOutput(String, int) are stored.
The returned path may change over time if the calling app is moved to an adopted storage device, so only relative paths should be persisted.
No additional permissions are required for the calling app to read or write files under the returned path.

# SharedPreference #


一般存用户的一些设置项，不会使用文件操作，而是直接使用android已经提供好的一个类SharedPreference

从名字就可以看出是存储用户prefer的一些东西，例如个用户prefer checkbox一直是checked的状态，这样下次登录时，就不用再输入用户名和密码。

现在就将之前的文件操作替换为sharedpreference

sharedpreference的操作很简单，就是直接get 然后 editor 然后 commit


    第一步会获取一个实例，并自动生成一个settings.xml文件
	SharedPreferences sharedPreferences = getSharedPreferences("settings",0);
	获取edito
    SharedPreferences.Editor editor = sharedPreferences.edit();
	放值
	editor.putString("name",name);
	editor.putString("passwd",passwd);
	editor.putBoolean("isChecked",checkBox.isChecked());
	提交
    editor.commit();

下面是获取操作，先getsharedpreference，然后使用各种获取函数就okay

    SharedPreferences sharedPreferences = getSharedPreferences("settings",0);
    String name = sharedPreferences.getString("name","");
    String passwd = sharedPreferences.getString("passwd","");
    boolean isChecked = sharedPreferences.getBoolean("isChecked",false);


