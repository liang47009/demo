package com.yunfeng.mvvm;

/**
 * aa
 * Created by xll on 2018/9/25.
 */
public class PackageInfo {
//    MVVM
//    终于轮到今天的主角出场了，MVVM模式不是四层，任然是3层，分别是Model、View、ViewModel
//
//    Model ：负责数据实现和逻辑处理，类似MVP。
//    View ： 对应于Activity和XML，负责View的绘制以及与用户交互，类似MVP。
//    ViewModel ： 创建关联，将model和view绑定起来,如此之后，我们model的更改，通过viewmodel反馈给view,从而自动刷新界面。
//
//    关于MVVM的各层职责的详细划分，大家可以参考知乎上的这篇文章。
//    https://www.zhihu.com/question/30976423
//
//    通常情况下，数据的流向是单方面的，只能从代码流向UI，也就是单向绑定；而双向绑定的数据流向是双向的，当业务代码中的数据改变时，UI上的数据能够得到刷新；
//    当用户通过UI交互编辑了数据时，数据的变化也能自动的更新到业务代码中的数据上。而DataBinding是一个实现数据和UI绑定的框架，是构建MVVM模式的一个关键的工具，它是支持双向绑定的。
//
//    双向绑定.png
//    下面通过一张图来来看看MVVM的各层是如何协同工作的。
//
//    mvvm协同工作.png
//    下面就一起来看看他们的职责和协同工作的原理。
//
//    Model
//    Model层就是职责数据的存储、读取网络数据、操作数据库数据以及I/O，一般会有一个ViewModel对象来调用获取这一部分的数据。
//
//    View
//    我感觉这里的View才是真正的View，为什么这么说？View层做的仅仅和UI相关的工作，我们只在XML、Activity、Fragment写View层的代码，View层不做和业务相关的事，也就是我们的Activity 不写和业务逻辑相关代码，
//    一般Activity不写更新UI的代码，如果非得要写，那更新的UI必须和业务逻辑和数据是没有关系的，只是单纯UI逻辑来更新UI，比如：滑动时头部颜色渐变、editttext根据输入内容显示隐藏等，
//    简单的说：View层不做任何业务逻辑、不涉及操作数据、不处理数据、UI和数据严格的分开。
//
//    ViewModel
//    ViewModel 只做和业务逻辑和业务数据相关的事，不做任何和UI、控件相关的事，ViewModel 层不会持有任何控件的引用，更不会在ViewModel中通过UI控件的引用去做更新UI的事情。
//    ViewModel就是专注于业务的逻辑处理，操作的也都是对数据进行操作，这些个数据源绑定在相应的控件上会自动去更改UI，开发者不需要关心更新UI的事情。
//
//    总结一下：View层的Activity通过DataBinding生成Binding实例,把这个实例传递给ViewModel，ViewModel层通过把自身与Binding实例绑定，从而实现View中layout与ViewModel的双向绑定。
//    mvvm的缺点数据绑定使得 Bug 很难被调试。你看到界面异常了，有可能是你 View 的代码有 Bug，也可能是 Model 的代码有问题。数据绑定使得一个位置的 Bug 被快速传递到别的位置，要定位原始出问题的地方就变得不那么容易了。
//
//    作者：唠嗑008
//    链接：https://www.jianshu.com/p/4830912f5162
//    來源：简书
//    简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
}
