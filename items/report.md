# [G0 - Team Name] Report

The following is a report template to help your team successfully provide all the details necessary for your report in a structured and organised manner. Please give a straightforward and concise report that best demonstrates your project. Note that a good report will give a better impression of your project to the reviewers.

Note that you should have removed ALL TEMPLATE/INSTRUCTION textes in your submission (like the current sentence), otherwise it hampers the professionality in your documentation.

*Here are some tips to write a good report:*

* `Bullet points` are allowed and strongly encouraged for this report. Try to summarise and list the highlights of your project (rather than give long paragraphs).*

* *Try to create `diagrams` for parts that could greatly benefit from it.*

* *Try to make your report `well structured`, which is easier for the reviewers to capture the necessary information.*

*We give instructions enclosed in square brackets [...] and examples for each sections to demonstrate what are expected for your project report. Note that they only provide part of the skeleton and your description should be more content-rich. Quick references about markdown by [CommonMark](https://commonmark.org/help/)*

## Table of Contents

1. [Team Members and Roles](#team-members-and-roles)
2. [Summary of Individual Contributions](#summary-of-individual-contributions)
3. [Application Description](#application-description)
4. [Application UML](#application-uml)
5. [Application Design and Decisions](#application-design-and-decisions)
6. [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
7. [Testing Summary](#testing-summary)
8. [Implemented Features](#implemented-features)
9. [Team Meetings](#team-meetings)
10. [Conflict Resolution Protocol](#conflict-resolution-protocol)

## Administrative
- Firebase Repository Link: <insert-link-to-firebase-repository>
    - Confirm: I have already added comp21006442@gmail.com as a Developer to the Firebase project prior to due date.
- Two user accounts for markers' access are usable on the app's APK (do not change the username and password unless there are exceptional circumstances. Note that they are not real e-mail addresses in use):
    - Username: comp2100@anu.edu.au	Password: comp2100
    - Username: comp6442@anu.edu.au	Password: comp6442

## Team Members and Roles
The key area(s) of responsibilities for each member

| UID   |      Name      |                              Role |
|:------|:--------------:|----------------------------------:|
| [u7709429] | [Tianhao Shan] |                              [UI] |
| [u7776634] | [Haochen Gong] |                  [Data structure] |
| [u7755061] |  [Yusi Zhong]  | [Data prepare] |
| [u7733037] |     [Hongjun Xu]     |                          [Search] |
| [u7725171] |     [Xing Chen]     |                            [role] |


## Summary of Individual Contributions

Specific details of individual contribution of each member to the project.

Each team member is responsible for writing **their own subsection**.

A generic summary will not be acceptable and may result in a significant lose of marks.

*[Summarise the contributions made by each member to the project, e.g. code implementation, code design, UI design, report writing, etc.]*

*[Code Implementation. Which features did you implement? Which classes or methods was each member involved in? Provide an approximate proportion in pecentage of the contribution of each member to the whole code implementation, e.g. 30%.]*

*you should ALSO provide links to the specified classes and/or functions*
Note that the core criteria of contribution is based on `code contribution` (the technical developing of the App).

*Here is an example: (Note that you should remove the entire section (e.g. "others") if it is not applicable)*

1. **u7709429, Tianhao Shan**  I have 20% contribution, as follows: <br>
- **Code Contribution in the final App**
    - Feature A1, A2, A3 - class Dummy: [Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java)
    - XYZ Design Pattern -  class AnotherClass: [functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43), [function2()](the-URL)
    - ... (any other contribution in the code, including UI and data files) ... [Student class](../src/path/to/class/Student.java), ..., etc.*, [LanguageTranslator class](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... <br><br>

- **Code and App Design**
    - [What design patterns, data structures, did the involved member propose?]*
    - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* <br><br>

- **Others**: (only if significant and significantly different from an "average contribution")
    - [Report Writing?] [Slides preparation?]*
    - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* <br><br>

2. **u7776634, Haochen Gong**  I have 20% contribution, as follows: <br>

- **Code Contribution in the final App**
    - Feature [Data-Formats] - class JsonReader: [JsonReader.java](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/JsonReader.java)
    - Feature [Data-GPS] - class PostShareActivity (GPS part): [PostShareActivity.java](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/com/example/compendiumofmateriamedica/PostShareActivity.java?ref_type=heads#L234-280)
    - Feature [LoadShowData] - class PlantDetailShow: [PlantDetailShow.java](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/com/example/compendiumofmateriamedica/PlantDetailShow.java)
    - Factory Design Pattern -  class TreeGenerator(interface): [generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/TreeGenerator.java?ref_type=heads#L11-13), class UserTreeGenerator[generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/UserTreeGenerator.java?ref_type=heads#L20-38), class PlantTreeGenerator[generateTree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PostTreeGenerator.java?ref_type=heads#L27-76), class GeneratorFactory[tree()](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/GeneratorFactory.java?ref_type=heads#L22-41)
    - Storage structure of data(RB-tree) - class RBTree: [RBTree.java](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/RBTree.java), and class RBTreeNode: [RBTreeNode.java](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/RBTreeNode.java)
    - Class for Wrapping Tree Operation Methods(I completed the main parts of these classes, which were later refactored by a team member into the singleton pattern.) - class UserTreeManager: [UserTreeManager.java](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/UserTreeManager.java), class PlantTreeManager: [PlantTreeManager.java](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PlantTreeManager.java), and class PostTreeManager: [PostTreeManager.java](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PostTreeManager.java)
      <br><br>

- **Code and App Design**
    - *The process of data reading and tree generation is divided into different classes, thus decoupling the logic of data reading and tree generation and making the code more modular and flexible.*
    - *The tree generation process is carried out using the factory pattern, and the implemented factory methods centralize the control process of data reading and tree generation, simplifying the main logic. The caller only needs to call the tree method and pass in the corresponding parameters to get the corresponding red-black tree without caring about the specific details of the generation.*
    - *All tree-specific operation methods are encapsulated into the corresponding TreeManager class , making the code structure more clear , the caller can find all tree operation methods in one place , easy to manage and maintain .*
    - <br><br>

- **Others**:
    - Report writing: *
    <br><br>

3. **u7755061, Yusi Zhong**  I have 20% contribution, as follows: <br>
- **Code Contribution in the final App**
    - Feature [DataFiles] - [posts.json](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/res/raw/posts.json), [plants.json](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/res/raw/plants.json), [users.json](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/res/raw/users.json), [posts_stream.json](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/res/raw/posts_stream.json)
    - Feature [FB-Auth] - [UserRepository.java](), [LoginViewModel]()
  
- **Code and App Design**
    - *Chose JSON over XML for data storage due to better compatibility and performance within the app. JSON was selected because it is lightweight and easier to parse, which improved the app’s speed and responsiveness.*
    - *Data instances included real plant information and Wikipedia descriptions retrieved from APIs, while posts and user data were generated for development purposes.*
    - *Firebase provided a robust authentication mechanism, ensuring secure user logins and data protection. However, its persistence capabilities were limited due to data transfer restrictions and occasional instability.*
      <br><br>

- **Others**: (only if significant and significantly different from an "average contribution") 
    - Firebase: Initially implemented Firebase for data persistence but switched to local storage due to stability issues and data transfer limitations. This transition ensured better scalability and enhanced app reliability. The final submitted version does not include the related Firebase code, but integration attempts like firebase realtime database and could firestore are documented in the firebase_bug branch.*
    <br><br>

4. **u7733037, Hongjun Xu**  I have 20% contribution, as follows: <br>
- **Code Contribution in the final App**
    - Feature [Search] - class [CaptureFragment](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/com/example/compendiumofmateriamedica/ui/capture/CaptureFragment.java?ref_type=heads), class [SearchGrammarParser](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Parser/SearchGrammarParser.java?ref_type=heads), class [Token](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Parser/Token.java?ref_type=heads), class [Tokenizer](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Parser/Tokenizer.java?ref_type=heads), class ParserEventHandler, class SearchedResults
    - Feature [Search-Invalid] - class ParserEventHandler: getSearchedResultsFromParameters(), getSearchedResultsFromBlurParameter()
    - Facade Design Pattern - class PlantIdentification: getPlantNetAPIResult(), getPlantNetAPIResultOKHttp(), getFromWiki()
    - Singleton Design Pattern - class GeneralFunctions
    - HTTP API Request - class PlantIdentification
    - Some APP recyclerviewAdapters functions
    - Some getter function in TreeManagers
    - (Test)
    
- **Code and App Design**
    - Data structure - RBTree
        - By hashing the special values in the data, new instances are re-inserted to improve the efficiency of subsequent tree searches.
    - Facade Design Pattern
        - I proposed to encapsulate the complex http interaction process into a method of obtaining plant parameters through the image path through the appearance design pattern.
    - UI Design - Cooperation Tools
        - To facilitate the collaborative design and discussion of team projects, we tried to use the online design tool "Miro" to interact and determine the general appearance of the APP UI.
    - UI Design - Rounded Corners
        - In order to prevent visual impact when users use it, I added some rounded corners, a soft and friendly visual experience. It can reduce visual impact, create a more relaxed and approachable appearance, and increase users’ willingness to interact.
    - 
    
- **Others**: (only if significant and significantly different from an "average contribution")
    - [Report Writing?] [Slides preparation?]*
    - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* <br><br>

5. **u7725171, Xing Chen**  I have 20% contribution, as follows: <br>
- **Code Contribution in the final App**
    - Feature DataStream - class MyApp, NotificationService, NewEvent, NewEventHandler
    - Feature Interact-Micro - class PostAdapter, SocialFragment, SocialViewModel, PhotoDialogFragment
    - Feature Interact-Share - class PostShareActivity
    - Feature Interact-Noti - class MyApp, NotificationService, NewEvent, NewEventHandler, NotificationAdapter, MessagesActivity
    - Feature Data-Profile, Data-Formats - class ProfileFragment, ProfilePage, MyPost, PlantDiscovered
    - Singleton Design Pattern - class PostTreeManager, PlantTreeManager, UserTreeManager, NewEventHandler
    - Observer Design Pattern - class NewEventHandler, MainActivity, ProfileFragment
    - Camera use - class CaptureFragment
    - ImageLoader function
    - Some getter function
    -

- **Code and App Design**
    - Singleton Design Pattern
        - I proposed that applying this design pattern on the data structure manager and event handler will significantly reduce memory usage.
    - Capturing new events in Application layer
        - User can get notification anywhere during the app is running. This will be more user-friendly.
    - UI design
        - Show user level in profile page to give user a target to chase so that user could have momentum.

- **Others**: (only if significant and significantly different from an "average contribution")
    - [Report Writing?] [Slides preparation?]*
    - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* <br><br>


## Application Description

*XXX is a social application aimed at plant enthusiasts, providing detailed information about various plants, including image examples, common names, slugs, scientific names, genus, and family information, along with extensive textual descriptions. Users can obtain plant information by taking photos of plants they encounter or by searching directly using text. Additionally, users can post their own photos of plants on the social channel to share their discoveries and experiences with all users.*


### Application Use Cases and or Examples

*Targets Users:  People who are interested in plants or want to learn about them*

* *Users can take pictures of unknown plants encountered in life through the application, and the application will return the relevant information of the plant; At the same time, the user can choose to upload the photo of the plant to the social channel to share with other users, if the plant is discovered by the user for the first time, the user will increase the energy value of plant exploration after sharing, and at the same time store the plant information in the discovery book.*
* *Users can search for a specific plant by entering information about the plant (such as common name, etc.) to get detailed information about the plant, and the app will also provide posts posted by other users related to the plant.*
* *Users can like their favorite posts on social channels, and the users who are liked will receive a message reminder.*
* *Users can view their own relevant information in the profile interface, such as published posts, plant collection guides, and plant exploration energy values.*
  <br>

*Use Case Diagram:*
<br>

![ClassDiagramExample](media/_examples/Use Case Diagram.png)
<hr> 

### Application UML

![ClassDiagramExample](media/_examples/ClassDiagramExample.png) <br>
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
        * Class NewEventHandler, methods
    * *Reasons:*
        * Controlled Initialization: Guarantees managers are properly initialized with an RBTree<T> before use, preventing errors from premature use.
        * Resource Efficiency: Saves memory and processing power by preventing the creation of multiple instances of data management objects.
        * Thread Safety: The synchronized getInstance method ensures each manager is created once, protecting against data corruption in multi-thread environments.
        * Consistency and Integrity: Ensures all parts of the app work with the same data instance, maintaining data integrity and avoiding discrepancies.

3. *Observer Pattern*
    * *Objective: Enable user to get notification when user's posts are liked by others. We also use this in some UI updating case.*
    * *Code Locations: defined in*
        * Class NewEventHandler
        * Class
    * *Reasons:*
        * Our app is a social app so user will be interested in other user's action and reaction.
        * Decoupling between classes.
        * Realtime updates.
        * Better for system with massive number of users like ours.
4. Facade Pattern
    -   

<hr>

### Parser

### <u>Grammar(s)</u>
*[How do you design the grammar? What are the advantages of your designs?]*
*If there are several grammars, list them all under this section and what they relate to.*

由于我们的Parser主要用于数据的搜索语法上，而搜索语法的主要目的是为了让用户使用时能得到更精确或者更具有共性的的搜索内容，因此该语法主要考虑的是语法整体的兼容性以及它的可扩展性。更具体的来说就是当搜索项目的植物时用户可以通过植物所属的族群、植物的学名、具有相似描述的内容等信息精确到一定范围的植物，而当用户搜索植物相关的post时也可以根据相关的植物的信息与时间范围等属性更精确的找到相关的post。

此外考虑到数据的各种属性会随着数据库的内容的丰富逐渐丰富数据的attribute，我们要一定程度上增强语法的兼容性即当出现新的attribute时，可以通过该语法将attribute和搜索内容一一对应。

因此，经过多方考虑目前的搜索语法主要可以分为三个部分，标签栏目、参数栏目与搜索方法栏目，每个栏目由一个标识符加内容框来表述。标识符分别为

|              | 栏目标识符 |
| ------------ | ---------- |
| 标签栏目     | #:         |
| 参数栏目     | $:         |
| 搜索方法栏目 | *:         |

而在搜索方法栏目上依据当前app的需要，暂时定为AND和OR两种搜索逻辑分别表示更精确的搜索以及更广泛的搜索。
每个标识符的内容通过,区分，并且可以自由分配。这种设计可以让标签内容与参数内容一一对应，并且便于使用者记忆，搜索语法的表达方式就是 `[]: {}`, 这种格式的堆叠。

Production Rules:

     <Exp>        := <TagColumn>, <TextColumn>, <METHOD> | <TextColumn>, <TagColumn>, <METHOD>
     <TagColumn>  := #: { <Content> },
     <TextColumn> := $: { <Content> },
     <Method>     := *: {&} | *: {|}
     <Content>    := STR | STR, <Content>

综上，当前的语法具有一定的兼容性、可扩展性与易读性。

---



Since our Parser is mainly used for the syntax of data searching, and the main purpose of the search syntax is to allow users to obtain more accurate or more generic search results, the syntax mainly considers the compatibility of the overall syntax and its scalability. More specifically, when searching for plants in the search project, users can accurately search for plants within a certain range based on information such as the plant's family, scientific name, or content with similar descriptions. Similarly, when users search for plant-related posts, they can also find related posts more accurately based on information about the relevant plants and attributes such as time range.

In addition, considering that various attributes of the data will gradually enrich as the content of the database expands, we need to enhance the compatibility of the syntax to some extent so that when new attributes appear, they can be matched with search content through the syntax one by one.

Therefore, after careful consideration, the current search syntax can be mainly divided into three parts: tag columns, parameter columns, and search method columns, each represented by an identifier followed by a content box. The identifiers are as follows:

|            | Column Identifier |
| ---------- | ----------------- |
| Tag Column | #:                |
| Param Column | $:                |
| Search Method Column | *:                |

Regarding the search method column, according to the current needs of the app, it is temporarily defined as two search logics, AND and OR, representing more accurate search and broader search respectively. The content of each identifier is separated by commas and can be freely assigned. This design allows the tag content to correspond one-to-one with the parameter content and is easy for users to remember. The expression of the search syntax is in the format of `[]: {}`, stacked in this format.

Production Rules:

     <Exp>        := <TagColumn>, <TextColumn>, <METHOD> | <TextColumn>, <TagColumn>, <METHOD>
     <TagColumn>  := #: { <Content> },
     <TextColumn> := $: { <Content> },
     <Method>     := *: {&} | *: {|}
     <Content>    := STR | STR, <Content>

In summary, the current syntax has a certain level of compatibility, scalability, and readability.



### <u>Tokenizers and Parsers</u>

*[Where do you use tokenisers and parsers? How are they built? What are the advantages of the designs?]*

<hr>



为了处理人机交互界面的输入问题，目前我们的分词器主要在两个场景下使用，分别是搜索的语法内容以及发布帖子的文字内容。

首先对于搜索的语法内容，为了提高解析器的识别效率并将词法记号化，我们需要在用户使用语法搜索时先将输入的单一字串转化为Token列表，再进行后续的语法处理。这样的好处是可以确保在进入Parser之前用户所输入的语法符号符合预期（不存在非法字符或者乱码），以提高语法逻辑的处理效率。

由于普遍的自然语言理解逻辑是单向的，我们在处理分词器的逻辑时时会按照读取的串流依次排查，如果当前输入的字符匹配到关键字符则提取当前字符并将其放入词组列表中，反之则继续堆叠当前的字符直到找到下一个对应的字符组为止。

具体的算法表达如下：


```
  1. 初始化一个空的 Token List 对象，用于存储所有的 Token。
  2. 当前还有未处理的 Token 时，进入循环。
     3. 在循环中，将当前 Token 添加到 Token List 列表中。

         4. 去除当前缓冲区中的空白字符。
         5. 如果缓冲区已经为空，则将当前 Token 设为 null，表示没有剩余的 Token，然后结束方法。
         6. 获取缓冲区中的第一个字符，并根据不同的字符类型来识别 Token 的类型。
         7. 根据第一个字符的不同，生成相应的 Token 对象，并将其设为当前 Token。
         8. 如果第一个字符是满足字符要求，则进入循环，依次读取连续的字符，直到遇到不满足字符要求的字符为止，
         	9. 将这一部分字符作为一个 Token 的字符串，生成 Token 对象并设为当前 Token。
         10. 如果第一个字符不是以上任何一种情况，则抛出异常，表示遇到了意外的 Token。
         11. 将当前 Token 从缓冲区中移除。

     12. 重复步骤 3 ，直到没有剩余的 Token。
  13. 返回存储了所有 Token 的 Token List。
```

接下来对于搜索语法的算法，我们可以参照前文 `Grammar(s)` 中提到的详细描述将语法的识别拆分为多个子逻辑块逐一处理，从最表层的`ExpParser` 到最底层的`ContentParser`，分别进行语义的识别。由于在最初的语法设计时就考虑到了语法的复杂性问题，每一个字逻辑快的处理都非常的简单，通常我们只需要确认子字串的开头和结尾有没有出现预期字符即可，这大大的提高了语法处理的效率。而程序后端在处理时会将标签栏目和参数栏目的所有子字串一一对应集成为一个HashMap 回传到前端，同时回传当前的搜索方法。接下来我们就可以通过得到的搜索语法参数和搜索方法调用红黑树的search() 进行后续的进一步处理，直到我们得到满足用户语法的所有植物/Post ID列表为止。

而当用户输入错误的Token或者语法时，我们同样可以隔离当前Token搜索栏对应的内容并检索后续的部分，而不需要像单一的字符串一样，当遇到错误时直接跳出整个语法处理逻辑。

此外当用户发布帖子时，我们同样会对其输入的文字进行分词处理。这样做可以快速定位用户输入的关键词，使我们能够快速实现敏感词汇的过滤功能，有效屏蔽不当内容。为了方便其他用户高效地搜索帖子内容，我们会对帖子进行分词处理，并逐个检索关键词。这样的处理方式可以使搜索方法更加高效快速。

---



To address the input issues in human-computer interaction, our tokenizer is currently mainly used in two scenarios: the syntax of searches and the textual content of posted threads.

Firstly, concerning the syntax of searches, in order to enhance the efficiency of the parser and tokenize the lexemes, we first convert the user's syntax input into a list of tokens before proceeding with subsequent syntax processing. This approach ensures that the syntax symbols input by the user meet expectations (no illegal characters or garbled code) before entering the parser, thereby improving the efficiency of syntax logic processing.

Since the logic of natural language understanding is generally unidirectional, our tokenizer logic sequentially examines the input stream to extract the current character if it matches a key character, placing it into the token list, and continues stacking the current character until the next corresponding character group is found.

The algorithmic expression is detailed as follows:

```
1. Initialize an empty Token List object to store all tokens.
2. Enter a loop while there are still unprocessed tokens.
   3. Add the current token to the Token List in the loop.
      4. Trim the whitespace from the current buffer.
      5. If the buffer is empty, set the current token to null to indicate no remaining tokens and then end the method.
      6. Get the first character in the buffer and identify the token type based on different character types.
      7. Generate the corresponding Token object based on the first character and set it as the current token.
      8. If the first character satisfies the character requirements, enter a loop and read consecutive characters until encountering a character that does not meet the requirements.
         9. Treat this part of characters as a token string, generate a Token object, and set it as the current token.
      10. If the first character does not match any of the above cases, throw an exception indicating an unexpected token.
      11. Remove the current token from the buffer.
   12. Repeat step 3 until there are no remaining tokens.
13. Return the Token List storing all tokens.
```

Next, for the algorithm of search syntax, we can split the recognition of syntax into multiple sub-logic blocks, as described in the previous section on `Grammar(s)`, processing from the top-level `ExpParser` to the bottom-level `ContentParser` to identify semantics. Since the complexity of syntax was considered during initial syntax design, the processing of each logic block is relatively simple. Usually, we only need to confirm whether the substring has the expected characters at the beginning and end, greatly enhancing the efficiency of syntax processing. When handling the backend, all substrings of tag columns and parameter columns are integrated into a HashMap and transmitted back to the frontend, along with the current search method. Then, further processing can be performed by calling the search() method of a red-black tree based on the obtained search syntax parameters and methods until obtaining a list of all plant/Post IDs satisfying the user's syntax.

When users input incorrect tokens or syntax, we can also isolate the content corresponding to the current token search column and search the subsequent part, without needing to exit the entire syntax processing logic, as with a single string.

Additionally, when users post threads, we similarly tokenize their textual inputs. This enables quick identification of user-entered keywords, allowing us to rapidly implement a sensitive word filtering function to effectively block inappropriate content. To facilitate other users' efficient search for thread content, we tokenize threads and search for keywords one by one. This processing approach makes search methods more efficient and faster.

### Others

*[What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*

<br>
<hr>

## Implemented Features
*[What features have you implemented? where, how, and why?]* <br>
*List all features you have completed in their separate categories with their featureId. THe features must be one of the basic/custom features, or an approved feature from Voice Four Feature.*

### Basic Features
1. [LogIn]. Description of the feature ... (easy)
    * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
    * Description of feature: ... <br>
    * Description of your implementation: ... <br>

2. [DataFiles]. Create at least 2500 valid data instances (easy)
    * Code to the Data File [posts.json](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/res/raw/posts.json), [plants.json](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/res/raw/plants.json), [users.json](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/res/raw/users.json), [posts_stream.json](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/res/raw/posts_stream.json)
    * Link to the Firebase repo: https://console.firebase.google.com/project/gp-24s1-fb08c/overview?hl=zh-cn
    * Plants includes plant names, images, common names, scientific names, genus, family, and extensive descriptions, it sourced from APIs, ensuring accuracy and relevance.
    * Posts and users were generated for development purposes, providing a realistic dataset for testing the app’s social features.

3. [LoadShowData]. Implemented a page that shows information about a single plant instance.
    * Code: class PlantDetailShow: [PlantDetailShow.java](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/com/example/compendiumofmateriamedica/PlantDetailShow.java)
    * The page will search for the plant in the generated plantTree based on the plant id passed in, and add the relevant attributes of the plant instance to the textView of the page.

4. [DataStream]. After a user logs in, a background service periodically generates events where random users like the current app user's post. The user can see notifications of new events and handle them.
    * Code: NotificationService.java
    * The service will generate a new event representing other user's like action periodically.
    * The service will also simulate other user sharing posts, reading post information from json.
    * The social page can do real-time update when someone share a new post.
    * If the app user's newest post has less than 6 likes, it will be liked by a random user.
    * User will get notification both as system notification and UI updates.
   <br>
5. [Search]. 

### Custom Features
Feature Category: Privacy <br>
1. [Privacy-Request]. Description of the feature  (easy)
    * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
    * Description of your implementation: ... <br>
      <br>

2. [Privacy-Block]. Description ... ... (medium)
   ... ...
   <br><br>

Feature Category: Firebase Integration <br>
3. [FB-Auth]. Implemented Firebase Authentication for secure user login and management. Users authenticate using their email and password (easy)
    * Code: [Class X, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
    * [Class B](../src/path/to/class/file.java#L30-85): methods A, B, C, lines of code: 30 to 85
    * The UserRepository class handles user authentication, while the LoginActivity class manages user login UI and interaction. Upon successful authentication, users are redirected to the main activity of the application.


Feature Category: User Interactivity <br>
4. [Interact-Micro] User can like other users' posts by clicking the like button
    * Code: class PostAdapter
    * If the post is already liked by user, clicking like button will unlike it.

5. [Interact-Share] User can share post after taking photo of plants
    * Code: class PostShareActivity, CaptureFragment
    * Click the camera icon in Capture page, the app will call camera of the cellphone.
    * After taking a photo of plant, user can post this photo with some content and share it with other user in Social page.

6. [Interact-Noti] User can get notifications when user's post is liked.
    * Code: class NotificationService
    * If user's newest post has less than 6 likes, the backstage service will simulate other user liking this post.
    * User can see how many unread messages he has now. After checking the messages, the number will be reset to 0.
    * If the unread notifications are more than 3, user will get system notification.

Feature Category: Greater Data Usage, Handling and Sophistication <br>
7. [Data-Profile] Profile page has a user level icon.
    * Code: class ProfileFragment, ProfilePage, PostAdapter
    * Based on how many plants user has discovered, the profile page will display different level icon.
    * There is a process bar showing how many plants left to level up.
    * User level will also be shown in his posts.

8. [Data-Formats] We read JSON and xxx file from local files.
    * Code: class JsonReader 还有哪里读了文件？？图片？？
    * Read from JSON: We read local JSON files(all data of our app) from the `res/raw` directory in our Android app, then we use the `JsonReader` class to open the resource file as an `InputStream`, read its contents with a `BufferedReader`, and parse the data into an `ArrayList` of `JSONObject`s.
    * Read picture from url?: 待完成

9. [Data-GPS] Users can get information about their current location
    * Code: class PostShareActivity    profile是怎么获取的？？
    * When a user shares a post, the user's current gps location is automatically obtained
    * The user's profile page also displays the current gps location.
   
10. [Data-Deletion] Users can delete their own posts.
    * Code: class PostTreeManager 还需要具体实现删除post的代码
    * Users can delete their own post, the specific logic for the red-black tree node deletion operations.


<hr>

### Surprise Features

1. [Using singleton Design Pattern]
  - We found that because we instantiate trees and TreeManagers in  several activities and in fact we are using the same one.
  - This requires more memory and is hard for activities to synchronize data.
  - Thus, we apply singleton design pattern on all of this TreeManagers and use getInstance() to get the unique instance.
  - By doing this, our app requires less memory and the code is more neat and readable.

2. [Refactored the return value of all search methods in all treeManager class]
  - When checking the code, we noticed that the search method in the TreeManager class returns a list of tree nodes. This requires an extra step to call the `getValue` method on the tree nodes to obtain the actual instances, indicating incomplete encapsulation.
  - So we'll perform the operation of getting the node value earlier in the search method, thus changing the return value of all search methods from a list of nodes to a list of instances.
  - By doing this, we make it easier for the backend to make calls to the search method, increasing the readability and ease of use of the code.


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

2. Tests for Singleton
    - Code: [SingletonTest Class, entire file](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/androidTest/java/com/example/compendiumofmateriamedica/SingletonTest.java) for the [PostTreeManager](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PostTreeManager.java?ref_type=heads#L26-49), [UserTreeManager](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/UserTreeManager.java?ref_type=heads#L19-37), [PlantTreeManager](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Datastructure/PlantTreeManager.java?ref_type=heads#L17-35) and [NotificationAdapter](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/src/app/src/main/java/model/Adapters/NotificationAdapter.java?ref_type=heads#L33-57) Class.
    - *Number of test cases: 12*
    - *Code coverage: [SingletonTest Class](media/Screenshots/OneOfSingletonTest.jpg), [Example Case](media/Screenshots/SingletonTest.jpg), [Result](media/Screenshots/SingletonTestResult.jpg)*
    - *Types of tests created and descriptions: Using reflection to create newinstance, getInstance() and multi-thread environment for each class*

...

<br> <hr>


## Team Management

### Meetings Records
* Link to the minutes of your meetings like above. There must be at least 4 team meetings.
  (each commited within 2 days aftre the meeting)
* Your meetings should also have a reasonable date spanning across Week 6 to 11.*

- *[Team Meeting 1](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/items/meeting-1.md)*
- *[Team Meeting 2](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/items/meeting-2.md)*
- *[Team Meeting 3](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/items/meeting-3.md)*
- *[Team Meeting 4](https://gitlab.cecs.anu.edu.au/u7733037/gp-24s1/-/blob/main/items/meeting-4.md)*

<hr>

### Conflict Resolution Protocol
*[Write a well defined protocol your team can use to handle conflicts. That is, if your group has problems, what is the procedure for reaching consensus or solving a problem?
(If you choose to make this an external document, link to it here)]*

This shall include an agreed procedure for situations including (but not limited to):
- e.g., if a member fails to meet the initial plan and/or deadlines
- e.g., if your group has issues, how will your group reach consensus or solve the problem?
- e.g., if a member gets sick, what is the solution? Alternatively, what is your plan to mitigate the impact of unforeseen incidents for this 6-to-8-week project? 
