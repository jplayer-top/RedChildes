**自定义开关按钮**

* 采用先使用后创建的思路

* 自动义控件类继承View

* 为方便使用自定义属性在values文件目录下创建attrs.xml
* 实现状态改变监听
* 继承View 的两个构造方法 1.参数（Context ）, 2.参数（Context, attrs）
* 继承OnMeature 对背景进行测量宽高, setMeasuredDimension(measureWidth, measureHeight);进行设定控件宽高
* 继承OnDraw，使用canvas.drawBitmap();画出控件样式
* 重写OnTouchEvent(),记录手指移动的方式并对上边的按钮进行赋值
* 创建回调接口，实现开关状态改变的监听。