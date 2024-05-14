# [G0 - Team Name] Report

The following is a report template to help your team successfully provide all the details necessary for your report in a structured and organised manner. Please give a straightforward and concise report that best demonstrates your project. Note that a good report will give a better impression of your project to the reviewers.

Note that you should have removed ALL TEMPLATE/INSTRUCTION textes in your submission (like the current sentence), otherwise it hampers the professionality in your documentation.

*Here are some tips to write a good report:*

* `Bullet points` are allowed and strongly encouraged for this report. Try to summarise and list the highlights of your project (rather than give long paragraphs).*
* *Try to create `diagrams` for parts that could greatly benefit from it.*
* *Try to make your report `well structured`, which is easier for the reviewers to capture the necessary information.*

*We give instructions enclosed in square brackets [...] and examples for each sections to demonstrate what are expected for your project report. Note that they only provide part of the skeleton and your description should be more content-rich. Quick references about markdown by [CommonMark](https://commonmark.org/help/)*

## Table of Contents

- [\[G0 - Team Name\] Report](#g0---team-name-report)
  - [Table of Contents](#table-of-contents)
  - [Administrative](#administrative)
  - [Team Members and Roles](#team-members-and-roles)
  - [Summary of Individual Contributions](#summary-of-individual-contributions)
  - [Application Description](#application-description)
    - [Application Use Cases and or Examples](#application-use-cases-and-or-examples)
    - [Application UML](#application-uml)
  - [Code Design and Decisions](#code-design-and-decisions)
    - [Data Structures](#data-structures)
    - [Design Patterns](#design-patterns)
    - [Parser](#parser)
    - [`<u>`Grammar(s)`</u>`](#ugrammarsu)
    - [`<u>`Tokenizers and Parsers`</u>`](#utokenizers-and-parsersu)
    - [Others](#others)
  - [Implemented Features](#implemented-features)
    - [Basic Features](#basic-features)
    - [Custom Features](#custom-features)
    - [Surprise Features](#surprise-features)
  - [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
  - [Testing Summary](#testing-summary)
  - [Team Management](#team-management)
    - [Meetings Records](#meetings-records)
    - [Conflict Resolution Protocol](#conflict-resolution-protocol)

## Administrative

- Firebase Repository Link: `<insert-link-to-firebase-repository>`
  - Confirm: I have already added comp21006442@gmail.com as a Developer to the Firebase project prior to due date.
- Two user accounts for markers' access are usable on the app's APK (do not change the username and password unless there are exceptional circumstances. Note that they are not real e-mail addresses in use):
  - Username: comp2100@anu.edu.au	Password: comp2100
  - Username: comp6442@anu.edu.au	Password: comp6442

## Team Members and Roles

The key area(s) of responsibilities for each member

| UID        |      Name      |             Role |
| :--------- | :------------: | ---------------: |
| [u7709429] | [Tianhao Shan] |             [UI] |
| [u7776634] | [Haochen Gong] | [Data structure] |
| [u7755061] |  [Yusi Zhong]  |   [Data prepare] |
| [u7733037] |  [Hongjun Xu]  |         [Search] |
| [u7725171] |  [Xing Chen]  |           [role] |

## Summary of Individual Contributions

Specific details of individual contribution of each member to the project.

Each team member is responsible for writing **their own subsection**.

A generic summary will not be acceptable and may result in a significant lose of marks.

*[Summarise the contributions made by each member to the project, e.g. code implementation, code design, UI design, report writing, etc.]*

*[Code Implementation. Which features did you implement? Which classes or methods was each member involved in? Provide an approximate proportion in pecentage of the contribution of each member to the whole code implementation, e.g. 30%.]*

*you should ALSO provide links to the specified classes and/or functions*
Note that the core criteria of contribution is based on `code contribution` (the technical developing of the App).

*Here is an example: (Note that you should remove the entire section (e.g. "others") if it is not applicable)*

1. **u7709429, Tianhao Shan**  I have 20% contribution, as follows: `<br>`

- **Code Contribution in the final App**

  - Feature A1, A2, A3 - class Dummy: [Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java)
  - XYZ Design Pattern -  class AnotherClass: [functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43), [function2()](the-URL)
  - ... (any other contribution in the code, including UI and data files) ... [Student class](../src/path/to/class/Student.java), ..., etc.*, [LanguageTranslator class](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... `<br><br>`
- **Code and App Design**

  - [What design patterns, data structures, did the involved member propose?]*
  - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* `<br><br>`
- **Others**: (only if significant and significantly different from an "average contribution")
  - [Report Writing?] [Slides preparation?]*
  - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* `<br><br>`

2. **u7776634, Haochen Gong**  I have 20% contribution, as follows: `<br>`

- **Code Contribution in the final App**

  - Feature A1, A2, A3 - class Dummy: [Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java)
  - XYZ Design Pattern -  class AnotherClass: [functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43), [function2()](the-URL)
  - ... (any other contribution in the code, including UI and data files) ... [Student class](../src/path/to/class/Student.java), ..., etc.*, [LanguageTranslator class](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... `<br><br>`
- **Code and App Design**

  - [What design patterns, data structures, did the involved member propose?]*
  - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* `<br><br>`
- **Others**: (only if significant and significantly different from an "average contribution")

  - [Report Writing?] [Slides preparation?]*
  - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* `<br><br>`

3. **u7755061, Yusi Zhong**  I have 20% contribution, as follows: `<br>`

- **Code Contribution in the final App**

  - Feature A1, A2, A3 - class Dummy: [Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java)
  - XYZ Design Pattern -  class AnotherClass: [functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43), [function2()](the-URL)
  - ... (any other contribution in the code, including UI and data files) ... [Student class](../src/path/to/class/Student.java), ..., etc.*, [LanguageTranslator class](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... `<br><br>`
- **Code and App Design**

  - [What design patterns, data structures, did the involved member propose?]*
  - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* `<br><br>`
- **Others**: (only if significant and significantly different from an "average contribution")

  - [Report Writing?] [Slides preparation?]*
  - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* `<br><br>`

4. **u7733037, Hongjun Xu**  I have 20% contribution, as follows: `<br>`

- **Code Contribution in the final App**

  - Feature A1, A2, A3 - class Dummy: [Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java)
  - XYZ Design Pattern -  class AnotherClass: [functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43), [function2()](the-URL)
  - ... (any other contribution in the code, including UI and data files) ... [Student class](../src/path/to/class/Student.java), ..., etc.*, [LanguageTranslator class](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... `<br><br>`
- **Code and App Design**

  - [What design patterns, data structures, did the involved member propose?]*
  - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* `<br><br>`
- **Others**: (only if significant and significantly different from an "average contribution")

  - [Report Writing?] [Slides preparation?]*
  - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* `<br><br>`

5. **u7725171, Xing Chen**  I have 20% contribution, as follows: `<br>`

- **Code Contribution in the final App**

  - Feature A1, A2, A3 - class Dummy: [Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java)
  - XYZ Design Pattern -  class AnotherClass: [functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43), [function2()](the-URL)
  - ... (any other contribution in the code, including UI and data files) ... [Student class](../src/path/to/class/Student.java), ..., etc.*, [LanguageTranslator class](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... `<br><br>`
- **Code and App Design**

  - [What design patterns, data structures, did the involved member propose?]*
  - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* `<br><br>`
- **Others**: (only if significant and significantly different from an "average contribution")

  - [Report Writing?] [Slides preparation?]*
  - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* `<br><br>`

## Application Description

*XXX is a social application aimed at plant enthusiasts, providing detailed information about various plants, including image examples, common names, slugs, scientific names, genus, and family information, along with extensive textual descriptions. Users can obtain plant information by taking photos of plants they encounter or by searching directly using text. Additionally, users can post their own photos of plants on the social channel to share their discoveries and experiences with all users.*

### Application Use Cases and or Examples

*Targets Users:  People who are interested in plants or want to learn about them*

* *Users can take pictures of unknown plants encountered in life through the application, and the application will return the relevant information of the plant; At the same time, the user can choose to upload the photo of the plant to the social channel to share with other users, if the plant is discovered by the user for the first time, the user will increase the energy value of plant exploration after sharing, and at the same time store the plant information in the discovery book.*
* *Users can search for a specific plant by entering information about the plant (such as common name, etc.) to get detailed information about the plant, and the app will also provide posts posted by other users related to the plant.*
* *Users can like their favorite posts on social channels, and the users who are liked will receive a message reminder.*
* *Users can view their own relevant information in the profile interface, such as published posts, plant collection guides, and plant exploration energy values.*
  `<br>`

*Use Case Diagram:*
`<br>`

![ClassDiagramExample](media/_examples/Use Case Diagram.png)

<hr>

### Application UML

![ClassDiagramExample](media/_examples/ClassDiagramExample.png) `<br>`
*[Replace the above with a class diagram. You can look at how we have linked an image here as an example of how you can do it too.]*

<hr>

## Code Design and Decisions

<hr>

### Data Structures

*I used the following data structures in my project:*

1. *Arraylist*

   * *Objective: used for storing JSONObject data read from a json file for [Data-Formats] feature.*
   * *Code Locations: processed using: storing data to Arraylist in [Class JsonReader, methods readJsonFromFile()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/JsonReader.java?ref_type=heads#L27-56); reading data from Arraylist in [generateTree() in Class UserTreeGenerator](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/UserTreeGenerator.java?ref_type=heads#L20-38) and [generateTree() in Class PlantTreeGenerator](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PlantTreeGenerator.java?ref_type=heads#L19-40) and [generateTree() in Class PostTreeGenerator](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PostTreeGenerator.java?ref_type=heads#L27-76) (All of these methods implement from [generateTree() in Interface TreeGenerator](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/TreeGenerator.java?ref_type=heads#L11-13))*
   * *Reasons:*
     * *Our scenario requires iterating over large datasets without needing to modify them. Therefore, Arraylist, with its contiguous storage and cache efficiency, is more suitable than complex structures like linked lists or hash tables due to its superior iteration performance.*
2. *Red-Black Tree*

   * *Objective: used for storing all the data in the app that needs to be read and loaded.*
   * *Code Locations: defined in [Class RBTree](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/RBTree.java?ref_type=heads#L14-335) and [Class RBTreeNode](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/RBTreeNode.java?ref_type=heads#L7-74); processed using: create tree in [generateTree() in Class UserTreeGenerator](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/UserTreeGenerator.java?ref_type=heads#L20-38) and [generateTree() in Class PlantTreeGenerator](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PlantTreeGenerator.java?ref_type=heads#L19-40) and [generateTree() in Class PostTreeGenerator](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PostTreeGenerator.java?ref_type=heads#L27-76); Insertion, deletion and search methods for trees in [all methods in Class UserTreeManager](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/UserTreeManager.java?ref_type=heads#L35-92) and [all methods in Class PlantTreeManager](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PlantTreeManager.java?ref_type=heads#L34-95) and [all methods in Class PostTreeManager](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PostTreeManager.java?ref_type=heads#L37-147)*
   * *Reasons:*
     * *For the three types of data in our application (user,plant,post), we need to do a lot of search operations, and all three types of data have unique ID. By using the ID as the key, red-black tree ensures that in the worst case, the time complexity of looking up any element is O(logN), which guarantees the efficiency of the searching.*
     * *For post data, frequent insertion and deletion operations are required, and the time complexity of red-black tree for insertion and deletion is also O(logN), which improves the efficiency in the dynamically changing data environment.*
     * *The data within the red-black tree is ordered, which makes it easy to do range queries, such as finding users or posts within a specific ID range.*
3. ...

<hr>

### Design Patterns

*[What design patterns did your team utilise? Where and why?]*

1. *Factory Pattern*

   * *Objective: used for the unified creation and configuration of red-black trees for different data types(user, plant and post).*
   * *Code Locations: defined in*
     *[Interface TreeGenerator](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/TreeGenerator.java?ref_type=heads#L11-13)*
     *[Class UserTreeGenerator, methods generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/UserTreeGenerator.java?ref_type=heads#L20-38)*
     *[Class PlantTreeGenerator, methods generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PlantTreeGenerator.java?ref_type=heads#L19-40)*
     *[Class PostTreeGenerator, methods generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PostTreeGenerator.java?ref_type=heads#L27-76)*
     *[Class GeneratorFactory, methods tree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/GeneratorFactory.java?ref_type=heads#L22-41)*
     *processed using*
     *[Class LoginActivity, methods DataInitial()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/com/example/compendiumofmateriamedica/LoginActivity.java?ref_type=heads#L173-177)*
   * *Reasons:*
     * The project needs to deal with three different data types, each with its own specific properties and behavior. By implementing a factory pattern, it is possible to design separate tree generators for each data type, e.g., UserTreeGenerator, PlantTreeGenerator, PostTreeGenerator.This design allows each generator to focus on the logic of handling a specific type of data, thus improving the readability and maintainability of the code. This improves code readability and maintainability.
     * Using the factory pattern allows apps to easily add new data type handlers in the future without affecting the existing code structure. By simply adding a new TreeGenerator implementation, new datatypes can be seamlessly integrated without modifying the core logic of the factory.
     * The factory method tree() provides a simple interface for clients to obtain the required data structures by encapsulating the details of data reading, parsing and tree generation. The client does not need to know the underlying implementation details, but only needs to specify the required data types and resource identifiers. This reduces the complexity of client-side operations and makes the code clearer and easier to manage.
     * The factory pattern also makes it easier to monitor and optimize the tree generation process. If a particular type of data tree generation is inefficient, the generator for that type can be targeted and optimized without modifying the processing logic for other types.
2. *Singleton Pattern*

   * *Objective: used for ensuring all components interacts with the same set of data, maintaining consistent operations such as insertions, deletions, and searches across activities.*
   * *Code Locations: defined in*
     * [Class PostTreeManager, methods getInstance()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PostTreeManager.java?ref_type=heads#L18-35)
     * [Class PlantTreeManager, methods getInstance()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PlantTreeManager.java?ref_type=heads#L21-32)
     * [Class UserTreeManager, methods getInstance()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/UserTreeManager.java?ref_type=heads#L21-32)
   * *Reasons:*
     * Controlled Initialization: Guarantees managers are properly initialized with an RBTree`<T>` before use, preventing errors from premature use.
     * Resource Efficiency: Saves memory and processing power by preventing the creation of multiple instances of data management objects.
     * Thread Safety: The synchronized getInstance method ensures each manager is created once, protecting against data corruption in multi-thread environments.
     * Consistency and Integrity: Ensures all parts of the app work with the same data instance, maintaining data integrity and avoiding discrepancies.

<hr>

### Parser

### Grammar(s）

*[How do you design the grammar? What are the advantages of your designs?]*
*If there are several grammars, list them all under this section and what they relate to.*

由于我们的Parser主要用于数据的搜索语法上，而搜索语法的主要目的是为了让用户使用时能得到更精确或者更具有共性的的搜索内容，因此该语法主要考虑的是语法整体的兼容性以及它的可扩展性。更具体的来说就是当搜索项目的植物时用户可以通过植物所属的族群、植物的学名、具有相似描述的内容等信息精确到一定范围的植物，而当用户搜索植物相关的post时也可以根据相关的植物的信息与时间范围等属性更精确的找到相关的post。

此外考虑到数据的各种属性会随着数据库的内容的丰富逐渐丰富数据的attribute，我们要一定程度上增强语法的兼容性即当出现新的attribute时，可以通过该语法将attribute和搜索内容一一对应。

因此，经过多方考虑目前的搜索语法主要可以分为三个部分，标签栏目、参数栏目与搜索方法栏目，每个栏目由一个标识符加内容框来表述。标识符分别为

| 栏目         | 标识符 |
| ------------ | ------ |
| 标签栏目     | #:     |
| 参数栏目     | $:     |
| 搜索方法栏目 | *:     |

而在搜索方法栏目上依据当前app的需要，暂时定为AND和OR两种搜索逻辑分别表示更精确的搜索以及更广泛的搜索。

每个标识符的内容通过`,`区分，并且可以自由分配。这种设计可以让标签内容与参数内容一一对应，并且便于使用者记忆，搜索语法的表达方式就是 `[]: {},`这种格式的堆叠。

Production Rules:

```
<Exp>        := <TagColumn>, <TextColumn>, <METHOD> | <TextColumn>, <TagColumn>, <METHOD>
<TagColumn>  := #: { <Content> },
<TextColumn> := $: { <Content> },
<Method>     := *: {&} | *: {|}
<Content>    := STR | STR, <Content>
```

综上，当前的语法具有一定的兼容性、可扩展性与易读性。



### Tokenizers and Parsers

*[Where do you use tokenisers and parsers? How are they built? What are the advantages of the designs?]*

<hr>
为了处理人机交互界面的输入问题，目前我们的分词器主要在两个场景下使用，分别是搜索的语法内容以及发布帖子的文字内容。

首先对于搜索的语法内容，为了提高解析器的识别效率并将词法记号化，我们需要在用户使用语法搜索时先将输入的单一字串转化为Token列表，再进行后续的语法处理。这样的好处是可以确保在进入Parser之前用户所输入的语法符号符合预期（不存在非法字符或者乱码），以提高语法逻辑的处理效率。

由于普遍的自然语言理解逻辑是单向的串流，我们在处理分词器的逻辑时时会按照读取的串流依次排查，如果当前输入的字符匹配到关键字符则提取当前字符并将其放入词组列表中，反之则继续堆叠当前的字符知道找到下一个对应的字符组为止。

```

1. 初始化一个空的 ArrayList<Token> 对象，用于存储所有的 Token。
2. 当前还有未处理的 Token 时，进入循环。
    3. 在循环中，将当前 Token 添加到 fullToken 列表中。

        1. 去除当前缓冲区中的空白字符。
        2. 如果缓冲区已经为空，则将当前 Token 设为 null，表示没有剩余的 Token，然后结束方法。
        3. 获取缓冲区中的第一个字符，并根据不同的字符类型来识别 Token 的类型。
        4. 根据第一个字符的不同，生成相应的 Token 对象，并将其设为当前 Token。
        5. 如果第一个字符是字母、数字或者中文字符，则进入循环，依次读取连续的字符，直到遇到不是字母、数字或中文字符的字符为止，然后将这一部分字符作为一个 Token 的字符串，类型为 STR，生成 Token 对象并设为当前 Token。
        6. 如果第一个字符不是以上任何一种情况，则抛出 IllegalTokenException 异常，表示遇到了意外的 Token。
        7. 将当前 Token 从缓冲区中移除。
        8. 方法结束。

    5. 重复步骤 3 和 4，直到没有剩余的 Token。
6. 返回存储了所有 Token 的 fullToken 列表。

```



### Others

*[What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*

<br>
<hr>

## Implemented Features

*[What features have you implemented? where, how, and why?]* `<br>`
*List all features you have completed in their separate categories with their featureId. THe features must be one of the basic/custom features, or an approved feature from Voice Four Feature.*

### Basic Features

1. [LogIn]. Description of the feature ... (easy)

   * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
   * Description of feature: ... `<br>`
   * Description of your implementation: ... `<br>`
2. [DataFiles]. Description  ... ... (...)

   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...
3. ...
   `<br>`

### Custom Features

Feature Category: Privacy `<br>`

1. [Privacy-Request]. Description of the feature  (easy)

   * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
   * Description of your implementation: ... `<br>`
     `<br>`
2. [Privacy-Block]. Description ... ... (medium)
   ... ...
   `<br><br>`

Feature Category: Firebase Integration `<br>`
3. [FB-Auth] Description of the feature (easy)

* Code: [Class X, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
* [Class B](../src/path/to/class/file.java#L30-85): methods A, B, C, lines of code: 30 to 85
* Description of your implementation: ... `<br>`

<hr>

### Surprise Features

- If implemented, explain how your solution addresses the task (any detail requirements will be released with the surprise feature specifications).
- State that "Suprised feature is not implemented" otherwise.

<br> <hr>

## Summary of Known Errors and Bugs

*[Where are the known errors and bugs? What consequences might they lead to?]*
*List all the known errors and bugs here. If we find bugs/errors that your team does not know of, it shows that your testing is not thorough.*

*Here is an example:*

1. *Bug 1:*

   - *A space bar (' ') in the sign in email will crash the application.*
   - ...
2. *Bug 2:*
3. ...

<br> <hr>

## Testing Summary

*[What features have you tested? What is your testing coverage?]*
*Please provide some screenshots of your testing summary, showing the achieved testing coverage. Feel free to provide further details on your tests.*

*Here is an example:*

1. Tests for Search

   - Code: [TokenizerTest Class, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java) for the [Tokenizer Class, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43)
   - *Number of test cases: ...*
   - *Code coverage: ...*
   - *Types of tests created and descriptions: ...*
2. xxx

...

<br> <hr>

## Team Management

### Meetings Records

* Link to the minutes of your meetings like above. There must be at least 4 team meetings.
  (each commited within 2 days aftre the meeting)
* Your meetings should also have a reasonable date spanning across Week 6 to 11.*

- *[Team Meeting 1](meeting-template.md)*
- ...
- ...
- [Team Meeting 4](link_to_md_file.md)
- ... (Add any descriptions if needed) ...

<hr>

### Conflict Resolution Protocol

*[Write a well defined protocol your team can use to handle conflicts. That is, if your group has problems, what is the procedure for reaching consensus or solving a problem?
(If you choose to make this an external document, link to it here)]*

This shall include an agreed procedure for situations including (but not limited to):

- e.g., if a member fails to meet the initial plan and/or deadlines
- e.g., if your group has issues, how will your group reach consensus or solve the problem?
- e.g., if a member gets sick, what is the solution? Alternatively, what is your plan to mitigate the impact of unforeseen incidents for this 6-to-8-week project?
