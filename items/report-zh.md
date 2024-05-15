# [G0 - 团队名称] 报告

以下是一个报告模板，可帮助您的团队以结构化和有组织的方式成功提供报告所需的所有详细信息。 请提供一份简单明了的报告，最能展示您的项目。 请注意，一份好的报告会让审阅者对您的项目有更好的印象。

请注意，您应该删除提交中的所有模板/说明文本（如当前句子），否则会妨碍文档的专业性。

*以下是撰写优秀报告的一些技巧：*

* 本报告允许并强烈鼓励使用“要点”。 尝试总结并列出您项目的亮点（而不是给出长段落）。*

* *尝试为可以从中受益匪浅的零件创建“图表”。*

* *尽量让你的报告“结构良好”，这样审稿人更容易捕获必要的信息。*

*我们提供方括号 [...] 中的说明以及每个部分的示例，以演示您的项目报告的预期内容。 请注意，它们只提供了部分骨架，您的描述应该内容更丰富。 [CommonMark](https://commonmark.org/help/)* 有关 Markdown 的快速参考

＃＃ 目录

1. [团队成员和角色](#team-members-and-roles)
2. [个人贡献摘要](#summary-of-individual-contributions)
3. [应用说明](#application-description)
4. [应用UML](#application-uml)
5. [应用程序设计和决策](#application-design-and-decisions)
6. [已知错误和缺陷摘要](#summary-of-known-errors-and-bugs)
7. [测试总结](#testing-summary)
8. [已实现的功能](#implemented-features)
9. [团队会议](#team-meetings)
10. [冲突解决协议](#conflict-resolution-protocol)

＃＃ 行政的

- Firebase 存储库链接：<insert-link-to-firebase-repository>
     - 确认：我已在截止日期之前将 comp21006442@gmail.com 作为开发人员添加到 Firebase 项目中。
- 应用程序的 APK 上可使用两个用于标记访问的用户帐户（除非有特殊情况，否则请勿更改用户名和密码。请注意，它们不是使用中的真实电子邮件地址）：
     - 用户名：comp2100@anu.edu.au 密码：comp2100
     - 用户名：comp6442@anu.edu.au 密码：comp6442

## 团队成员和角色

每个成员的关键职责领域

| UID | 名称 | 角色 |
| :--------- | :------------: | ---------------： |
| [u7709429] | 【天浩山】| [用户界面] |
| [u7776634] | 【宫浩辰】| [数据结构] |
| [u7755061] | [钟雨思] | [资料准备] |
| [u7733037] | [徐红军] | [搜索] |
| [u7725171] | [星辰] | [角色] |


## 个人贡献总结

每个成员对项目的个人贡献的具体细节。

每个团队成员负责编写**自己的小节**。

通用的总结是不可接受的，并且可能会导致明显的分数损失。

*[总结每个成员对项目所做的贡献，例如 代码实现、代码设计、UI设计、报告撰写等]*

*[代码实现。 您实现了哪些功能？ 每个成员涉及哪些类或方法？ 提供每个成员对整个代码实现的贡献的大致比例（百分比），例如 30%。]*

*您还应该提供指定类和/或函数的链接*
请注意，贡献的核心标准是基于“代码贡献”（App 的技术开发）。

*这是一个示例：（请注意，如果不适用，您应该删除整个部分（例如“其他”））*

1. **u7709429，天浩山** 我有20%的贡献，如下：<br>

   - **最终应用程序中的代码贡献**
     - 功能 A1、A2、A3 - 虚拟类：[Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media /_examples/Dummy.java)
     - XYZ 设计模式 - AnotherClass 类：[functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/ Dummy.java#L22-43), [function2()](the-URL)
     - ...（代码中的任何其他贡献，包括 UI 和数据文件） ... [Student 类](../src/path/to/class/Student.java), ..., 等等*, [LanguageTranslator 类](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... <br><br>

   - **代码和应用程序设计**
     - [相关成员提出了哪些设计模式、数据结构？]*
     - [用户界面设计。 请具体说明相关成员提出了什么设计？ 设计使用了哪些工具？]* <br><br>

   - **其他**：（仅当与“平均贡献”显着且显着不同时）
     - [撰写报告？] [准备幻灯片？]*
     - [欢迎您提供任何您认为对项目或团队有贡献的内容。] 例如，APK、设置，firebase* <br><br>

2. **u7776634，龚浩辰** 我有20%的贡献，如下：<br>

- **最终应用程序中的代码贡献**
     - 功能 A1、A2、A3 - 虚拟类：[Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media /_examples/Dummy.java)
     - XYZ 设计模式 - AnotherClass 类：[functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/ Dummy.java#L22-43), [function2()](the-URL)
     - ...（代码中的任何其他贡献，包括 UI 和数据文件） ... [Student 类](../src/path/to/class/Student.java), ..., 等等*, [LanguageTranslator 类](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... <br><br>

- **代码和应用程序设计**
     - [相关成员提出了哪些设计模式、数据结构？]*
     - [用户界面设计。 请具体说明相关成员提出了什么设计？ 设计使用了哪些工具？]* <br><br>

- **其他**：（仅当与“平均贡献”显着且显着不同时）
     - [撰写报告？] [准备幻灯片？]*
     - [欢迎您提供任何您认为对项目或团队有贡献的内容。] 例如 APK、设置、firebase* <br><br>

3. **u7755061，钟宇思** 我有20%的贡献，如下：<br>

- **最终应用程序中的代码贡献**
     - 功能 A1、A2、A3 - 虚拟类：[Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media /_examples/Dummy.java)
     - XYZ 设计模式 - AnotherClass 类：[functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/ Dummy.java#L22-43), [function2()](the-URL)
     - ...（代码中的任何其他贡献，包括 UI 和数据文件） ... [学生班级](../src/path/to/class/Student.java), ..., 等等*, [LanguageTranslator 类](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... <br><br>

- **代码和应用程序设计**
     - [相关成员提出了哪些设计模式、数据结构？]*
     - [用户界面设计。 请具体说明相关成员提出了什么设计？ 设计使用了哪些工具？]* <br><br>

- **其他**：（仅当与“平均贡献”显着且显着不同时）
     - [撰写报告？] [准备幻灯片？]*
     - [欢迎您提供任何您认为对项目或团队有贡献的内容。] 例如 APK、设置、firebase* <br><br>

4. **u7733037，徐红军** 我有20%的贡献，如下：<br>

- **最终应用程序中的代码贡献**
     - 功能 A1、A2、A3 - 虚拟类：[Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media /_examples/Dummy.java)
     - XYZ 设计模式 - AnotherClass 类：[functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/ Dummy.java#L22-43), [function2()](the-URL)
     - ...（代码中的任何其他贡献，包括 UI 和数据文件） ... [学生班级](../src/path/to/class/Student.java), ..., 等等*, [LanguageTranslator 类](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... <br><br>

- **代码和应用程序设计**
     - [相关成员提出了哪些设计模式、数据结构？]*
     - [用户界面设计。 请具体说明相关成员提出了什么设计？ 设计使用了哪些工具？]* <br><br>

- **其他**：（仅当与“平均贡献”显着且显着不同时）
     - [撰写报告？] [准备幻灯片？]*
     - [欢迎您提供任何您认为对项目或团队有贡献的内容。] 例如 APK、设置、firebase* <br><br>

5. **u7725171，陈星** 我有20%的贡献，如下：<br>

- **最终应用程序中的代码贡献**

     - 功能 A1、A2、A3 - 虚拟类：[Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media /_examples/Dummy.java)
     - XYZ 设计模式 - AnotherClass 类：[functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/ Dummy.java#L22-43), [function2()](the-URL)
     - ...（代码中的任何其他贡献，包括 UI 和数据文件） ... [学生班级](../src/path/to/class/Student.java), ..., 等等*, [LanguageTranslator 类](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... <br><br>

- **代码和应用程序设计**

     - [相关成员提出了哪些设计模式、数据结构？]*
     - [用户界面设计。 请具体说明相关成员提出了什么设计？ 设计使用了哪些工具？]* <br><br>

- **其他**：（仅当与“平均贡献”显着且显着不同时）

     - [撰写报告？] [准备幻灯片？]*
     - [欢迎您提供任何您认为对项目或团队有贡献的内容。] 例如，APK、设置、火力基地* <br><br>

​    

## 应用说明

*XXX是一款针对植物爱好者的社交应用程序，提供有关各种植物的详细信息，包括图像示例、俗名、蛞蝓、学名、属和科信息，以及广泛的文字描述。 用户可以通过拍摄遇到的植物照片或直接使用文字搜索来获取植物信息。 此外，用户可以在社交频道上发布自己的植物照片，与所有用户分享他们的发现和经验。*


### 应用程序用例和/或示例

*目标用户：对植物感兴趣或想了解植物的人*

**用户可以通过应用拍摄生活中遇到的未知植物，应用会返回植物的相关信息； 同时，用户可以选择将植物的照片上传到社交频道与其他用户分享，如果该植物是用户第一次发现的，则用户在以后会增加植物探索的能量值 分享，同时将植物信息储存在发现书里*
* *用户可以通过输入植物的信息（如俗名等）来搜索特定植物，以获取该植物的详细信息，并且应用程序还会提供其他用户发布的与该植物相关的帖子。*
* *用户可以在社交渠道上为自己喜欢的帖子点赞，被点赞的用户会收到消息提醒。*
* *用户可以在个人资料界面查看自己的相关信息，例如发表的帖子、植物采集指南、植物探索能量值等。*
     <br>

*用例图：*
<br>

![ClassDiagramExample](media/_examples/用例图.png)

<小时>


### 应用程序 UML

![ClassDiagramExample](E:\86316\Projects\Android\gp-24s1\items\report-zh.assets\ClassDiagramExample.png) <br>
*[用类图替换上面的内容。 您可以看看我们如何在此处链接图像，作为您也可以如何做到这一点的示例。]*

<小时>


## 代码设计和决策

<小时>


＃＃＃ 数据结构

*我在我的项目中使用了以下数据结构：*

1. *数组列表*
     * *目标：用于存储从 [Data-Formats] 功能的 json 文件读取的 JSONObject 数据。*
     * *代码位置：处理使用：将数据存储到[JsonReader类，方法readJsonFromFile()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src中的Arraylist /app/src/main/java/model/Datastruct/JsonReader.java?ref_type=heads#L27-56); 从UserTreeGenerator类中的[generateTree()]中的Arraylist读取数据(https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/ model/Datastruct/UserTreeGenerator.java?ref_type=heads#L20-38) 和 [PlantTreeGenerator 类中的generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/ main/src/app/src/main/java/model/Datastruct/PlantTreeGenerator.java?ref_type=heads#L19-40) 和 [PostTreeGenerator 类中的generateTree()](https://gitlab.cecs.anu.edu。 au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct/PostTreeGenerator.java?ref_type=heads#L27-76) （所有这些方法均从 [generateTree( ）在接口TreeGenerator]（https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct/TreeGenerator.java？ ref_type=头#L11-13))*
     * *原因：*
         * *我们的场景需要迭代大型数据集而不需要修改它们。 因此，Arraylist凭借其连续存储和缓存效率，由于其优越的迭代性能，比链表或哈希表等复杂结构更适合。*

2.*红黑树*
     * *目的：用于存储应用程序中需要读取和加载的所有数据。*
     * *代码位置：在[RBTree类]中定义(https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/ Datastruct/RBTree.java?ref_type=heads#L14-335) 和 [RBTreeNode 类](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/ src/main/java/model/Datastruct/RBTreeNode.java?ref_type=heads#L7-74); 处理使用：在 UserTreeGenerator 类中的 [generateTree() 中创建树](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java /model/Datastruct/UserTreeGenerator.java?ref_type=heads#L20-38) 和 [PlantTreeGenerator 类中的generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob /main/src/app/src/main/java/model/Datastruct/PlantTreeGenerator.java?ref_type=heads#L19-40) 和 [PostTreeGenerator 类中的generateTree()](https://gitlab.cecs.anu.edu .au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct/PostTreeGenerator.java?ref_type=heads#L27-76); [UserTreeManager类中的所有方法]中树的插入、删除和搜索方法(https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main /java/model/Datastruct/UserTreeManager.java?ref_type=heads#L35-92) 和 [所有方法PlantTreeManager 类中的 s](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct/PlantTreeManager.java? ref_type=heads#L34-95) 和 [PostTreeManager 类中的所有方法](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main /java/model/Datastruct/PostTreeManager.java?ref_type=heads#L37-147)*
     * *原因：*
         * *对于我们应用中的三类数据（用户、工厂、帖子），我们需要做大量的搜索操作，并且三类数据都有唯一的ID。 红黑树以ID为键，保证在最坏的情况下，查找任意元素的时间复杂度为O(logN)，保证了查找的效率。*
         * *对于post数据，需要频繁的插入和删除操作，红黑树插入和删除的时间复杂度也是O(logN)，提高了动态变化的数据环境下的效率。*
         * *红黑树内的数据是有序的，这样可以方便地进行范围查询，例如查找特定ID范围内的用户或帖子。*

3....

<小时>


### 设计模式

*[您的团队使用了哪些设计模式？ 在哪里以及为什么？]*

1.*工厂模式*
     * *目的：用于不同数据类型（用户、工厂、帖子）的红黑树的统一创建和配置。*
     * *代码位置：定义于*
         *[接口TreeGenerator](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct/TreeGenerator.java? ref_type=头#L11-13)*
         *[类UserTreeGenerator，方法generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct /UserTreeGenerator.java?ref_type=heads#L20-38)*
         *[类 PlantTreeGenerator，方法generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct /PlantTreeGenerator.java?ref_type=heads#L19-40)*
         *[类PostTreeGenerator，方法generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct /PostTreeGenerator.java?ref_type=heads#L27-76)*
         *[类 GeneratorFactory，方法 tree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct /GeneratorFactory.java?ref_type=heads#L22-41)*
         *处理使用*
         *[类 LoginActivity，方法 DataInitial()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/com/example /compendiumofmateriamedica/LoginActivity.java?ref_type=heads#L173-177)*
     * *原因：*
         * 该项目需要处理三种不同的数据类型，每种数据类型都有自己特定的属性和行为。 通过实现工厂模式，可以为每种数据类型设计单独的树生成器，例如UserTreeGenerator、PlantTreeGenerator、PostTreeGenerator。这种设计允许每个生成器专注于处理特定类型数据的逻辑，从而提高了可读性和 代码的可维护性。 这提高了代码的可读性和可维护性。
         * 使用工厂模式允许应用程序将来轻松添加新的数据类型处理程序，而不会影响现有的代码结构。 只需添加一个新的 TreeGenerator 实现，就可以无缝集成新的数据类型，而无需修改工厂的核心逻辑。
         * 工厂方法tree()通过封装数据读取、解析和树生成的细节，为客户端获取所需的数据结构提供了一个简单的接口。 客户端不需要知道底层实现细节，只需要指定所需的数据类型和资源标识符即可。 这降低了客户端操作的复杂性，使代码更清晰、更易于管理。
         * 工厂模式还使得监控和优化树生成过程变得更加容易。 如果特定类型的数据树生成效率低下，则可以针对该类型的生成器进行针对性优化，而无需修改其他类型的处理逻辑。

2.*单例模式*
     * *目标：用于确保所有组件与同一组数据交互，保持操作的一致性，例如跨活动的插入、删除和搜索。*
     * *代码位置：定义于*
         * [类 PostTreeManager，方法 getInstance()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct /PostTreeManager.java?ref_type=heads#L18-35)
         * [类 PlantTreeManager，方法 getInstance()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct /PlantTreeManager.java?ref_type=heads#L21-32)
         * [类 UserTreeManager，方法 getInstance()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastruct/UserTreeManager.java?ref_type=heads#L21-32)
     * *原因：*
         * 受控初始化：保证管理器在使用前使用 RBTree<T> 进行正确初始化，防止过早使用时出现错误。
         * 资源效率：通过防止创建数据管理对象的多个实例来节省内存和处理能力。
         * 线程安全：同步的 getInstance 方法确保每个管理器只创建一次，从而防止多线程环境中的数据损坏。
         * 一致性和完整性：确保应用程序的所有部分都使用相同的数据实例，保持数据完整性并避免差异。

<小时>


### 解析器

### <u>语法</u>

*[你如何设计语法？ 你们的设计有什么优点？]*
*如果有多种语法，请在本节下列出所有语法以及它们的相关内容。*

制作规则：

     <非终端> ::= <某些输出>
     <非终端> ::= <某些输出>


### <u>分词器和解析器</u>

*[在哪里使用标记器和解析器？ 它们是如何建造的？ 这些设计有什么优点？]*

<小时>


＃＃＃ 其他的

*[您还做出了哪些您认为相关的其他设计决策？ 请随意将它们分成自己的小标题。]*

<br>

<小时>


## 实现的功能

*[您实现了哪些功能？ 在哪里、如何以及为什么？]* <br>
*列出您已完成的所有功能及其功能 ID 的单独类别。 这些功能必须是基本/自定义功能之一，或者是 Voice Four 功能批准的功能。*

### 基本特征

1. [登录]。 功能描述...（简单）
     *代码：[类X，方法Z，Y]（https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy .java#L22-43) 和 Y 类，...
     * 功能描述：... <br>
     * 您的实施描述：... <br>

2. [数据文件]。 描述  ... ... （...）
     * 数据文件代码 [users_interaction.json]（链接到文件）、[search-queries.xml]（链接到文件）、...
     * Firebase 存储库链接：...

3....
     <br>

### 自定义功能

功能类别：隐私 <br>

1. [隐私请求]。 功能描述（简单）
     *代码：[类X，方法Z，Y]（https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy .java#L22-43) 和 Y 类，...
     * 您的实施描述：... <br>
         <br>

2. [隐私区块]。 说明……（中）
     ……
     <br><br>

功能类别：Firebase 集成 <br>

3. [FB-Auth] 功能说明（简单）
     *代码：[X类，整个文件](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java #L22-43) 和 Y 级，...
     * [Class B](../src/path/to/class/file.java#L30-85): 方法 A、B、C，代码行数：30 到 85
     * 您的实施描述：... <br>

<小时>


### 惊喜功能

- 如果实施，请解释您的解决方案如何解决该任务（任何详细要求将与令人惊讶的功能规范一起发布）。
- 否则说明“未实现令人惊讶的功能”。

<br><小时>

## 已知错误和缺陷摘要

*[已知的错误和bug在哪里？ 它们可能会导致什么后果？]*
*在此列出所有已知的错误和错误。 如果我们发现您的团队不知道的错误/错误，则表明您的测试不彻底。*

*这是一个例子：*

1.*错误1：*
     - *登录电子邮件中的空格键 (' ') 将使应用程序崩溃。*
     - ...

2.*错误2：*
3....

<br><小时>


## 测试总结

*[您测试了哪些功能？ 您的测试覆盖范围是多少？]*
*请提供一些测试摘要的屏幕截图，显示已实现的测试覆盖率。 请随时提供有关您的测试的更多详细信息。*

*这是一个例子：*

1. 搜索测试
     - 代码：[TokenizerTest 类，整个文件](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java ）对于[Tokenizer类，整个文件]（https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java #L22-43)
     - *测试用例数量：...*
     - *代码覆盖率：...*
     - *创建的测试类型和描述：...*

2.xxx

...

<br><小时>


＃＃ 团队管理

### 会议记录

* 链接到您的会议记录，如上所示。 必须至少召开 4 次团队会议。
     （每项在会议后2天内提交）
* 您的会议还应该有一个跨越第 6 周至第 11 周的合理日期。*


- *[团队会议 1](meeting-template.md)*
- ...
- ...
- [团队会议4](link_to_md_file.md)
- ...（如果需要，添加任何描述）...

<小时>


### 冲突解决协议

*[编写一个定义明确的协议，您的团队可以使用它来处理冲突。 也就是说，如果你的小组出现问题，达成共识或解决问题的程序是什么？
（如果您选择将其设为外部所有文档，链接到此处）]*

这应包括针对以下情况的商定程序，包括（但不限于）：

- 例如，如果成员未能遵守最初的计划和/或最后期限
- 例如，如果您的团队遇到问题，您的团队将如何达成共识或解决问题？
- 例如，如果成员生病了，解决办法是什么？ 或者，您计划如何减轻不可预见事件对这个为期 6 至 8 周的项目的影响？