package com.yunfeng.mvp;

/**
 * Created by xll on 2018/9/25.
 */

public class PackageInfo {
//    MVP
//    在Android开发中，Activity并不是一个标准的MVC模式中的Controller，本来它的首要职责是加载应用的布局和初始化用户界面，接受并处理来自用户的操作请求，进而作出响应。
//      在MVC模式下随着界面及其逻辑的复杂度不断提升，Activity类的职责不断增加，以致变得庞大臃肿。
//
//    视图层(View)
//    负责绘制UI元素、与用户进行交互，对应于xml、Activity、Fragment、Adapter
//
//    模型层(Model)
//    负责存储、检索、操纵数据，一般包含网络请求，数据库处理，I/O流。
//
//    控制层(Presenter)
//    Presenter是整个MVP体系的控制中心，作为View与Model交互的中间纽带，处理View于Model间的交互和业务逻辑。
//
//    从去年到现在，MVP的设计思想在项目中用得比较多，它的具体实现就是接收到View的请求，从Model层获取数据，将数据进行处理，通过View层的接口回调给Activity或者Fragment。
//          MVP能够让Activity成为真正的View，只做UI相关的事。它的优点还是很多的，不然也不会有这么多人喜欢它的，优点如下：
//
//            1、模型与视图完全分离，我们可以修改视图而不影响模型；
//            2、项目代码结构（文件夹）清晰，一看就知道什么类干什么事情；
//            3、我们可以将一个Presenter用于多个视图，而不需要改变Presenter的逻辑。这个特性非常的有用，因为视图的变化总是比模型的变化频繁
//            4、协同工作（例如在设计师没出图之前可以先写一些业务逻辑代码或者其他人接手代码改起来比较容易）
//
//    尽管这样，MVP模式也有不足之处，不然也不会推出MVVM了，缺点如下：
//
//    Presente层与View层是通过接口进行交互的，View层可能会有大量的接口，因为有可能好几个Activity都是去实现同一个View接口，那么所有用到的Activity都要去实现所有的方法(不管你是否用到)，
//    而且如果后面有些方法要删改，Presenter和Activity都要改动，比较麻烦；
//
//    MVP把Activity相当的一部分责任放到了Presenter来处理，复杂的业务同时也可能会导致P层太大，一旦业务逻辑越来越多，View定义的方法越来越多，会造成Activity和Fragment实现的方法越来越多，依然臃肿。
//
//    作者：唠嗑008
//    链接：https://www.jianshu.com/p/4830912f5162
//    來源：简书
//    简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
}
