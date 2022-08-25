### 设计模式使用说明

1. 简单工厂模式：`MahjongGameFactory`

通过一个工厂生成不同的游戏，省去外部switch-case的判断

2. 装饰者模式：`XXXMahjongGame`

`XXXMahjongGame`和`BaseMahjongGame`都实现了`IMahjongGame`接口，并通过组合的方式，为`BaseMahjongGame`装饰了各自的功能

3. 组合模式：`ILoopStep`

`ILoopStep`本身也是`IStep`，但是里面通过添加`IRoundStep`，实现了步骤套步骤、步骤可循环的内部功能，对外部调用来说，看上去仅仅是一个普通的`IStep`