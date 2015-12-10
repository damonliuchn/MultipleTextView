
## MultipleTextView

MultipleTextView can automatically arrange a plurality of TextView .

# attrs
```java
        <attr name="textColor" format="color" />
        <attr name="textSize" format="dimension" />
        <attr name="textWordMargin" format="dimension" />
        <attr name="textLineMargin" format="dimension" />
        <attr name="textBackground" format="integer" />
        <attr name="textPaddingLeft" format="dimension" />
        <attr name="textPaddingRight" format="dimension" />
        <attr name="textPaddingTop" format="dimension" />
        <attr name="textPaddingBottom" format="dimension" />
        <attr name="overspread" format="boolean" />
        <attr name="columnNum" format="integer" />
```
# Usage
```java
  <net.masonliu.multipletextview.library.MultipleTextViewGroup 
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/main_rl"
        android:layout_width="wrap_content"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:layout_height="wrap_content"
        app:textSize="12sp"
        app:textWordMargin="10dp"
        app:textLineMargin="10dp"
        app:textColor="#ff0000"
        app:textBackground="@color/grey"
        app:textPaddingLeft="10dp"
        app:textPaddingRight="10dp"
        app:textPaddingTop="10dp"
        app:textPaddingBottom="10dp"
        app:columnNum="2"
        app:overspread="false" />
```
```java
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.MasonLiuChn:MultipleTextView:1.0.2'
}
```

<img src="https://github.com/MasonLiuChn/MultipleTextView/raw/master/app/doc/2.png"  width="320" height="500"/>
<img src="https://github.com/MasonLiuChn/MultipleTextView/raw/master/app/doc/1.png"  width="320" height="500"/>

<img src="https://github.com/MasonLiuChn/MultipleTextView/raw/master/app/doc/4.png"  width="320" height="500"/>
<img src="https://github.com/MasonLiuChn/MultipleTextView/raw/master/app/doc/3.png"  width="320"/>

------
Blog:http://blog.csdn.net/masonblog

Email:MasonLiuChn@gmail.com
