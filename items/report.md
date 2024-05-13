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
    - Feature A1, A2, A3 - class Dummy: [Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java)
    - XYZ Design Pattern -  class AnotherClass: [functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43), [function2()](the-URL)
    - ... (any other contribution in the code, including UI and data files) ... [Student class](../src/path/to/class/Student.java), ..., etc.*, [LanguageTranslator class](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... <br><br>

- **Code and App Design**
    - [What design patterns, data structures, did the involved member propose?]*
    - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* <br><br>

- **Others**: (only if significant and significantly different from an "average contribution")
    - [Report Writing?] [Slides preparation?]*
    - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* <br><br>

3. **u7755061, Yusi Zhong**  I have 20% contribution, as follows: <br>
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

4. **u7733037, Hongjun Xu**  I have 20% contribution, as follows: <br>
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

5. **u7725171, Xing Chen**  I have 20% contribution, as follows: <br>
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

    
## Application Description

*XXX is a social application aimed at plant enthusiasts, providing detailed information about various plants, including image examples, common names, slugs, scientific names, genus, and family information, along with extensive textual descriptions. Users can obtain plant information by taking photos of plants they encounter or by searching directly using text. Additionally, users can post their own photos of plants on the social channel to share their discoveries and experiences with all users. 
<br><br>Upon launching the application, users first enter a login screen where they authenticate themselves using an email and password. 
<br><br>After logging in, the first page displayed is the social channel page, where all user-shared content is displayed in reverse chronological order, including plant photos and text. 
<br><br>By clicking the camera button on the navigation bar at the bottom of the page, users can access the application's core search page. 
<br><br>还没写完——————————————————————*

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

*[What data structures did your team utilise? Where and why?]*

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

1. *xxx Pattern*
   * *Objective: used for storing xxxx for xxx feature.*
   * *Code Locations: defined in [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
   * *Reasons:*
      * ...

<hr>

### Parser

### <u>Grammar(s)</u>
*[How do you design the grammar? What are the advantages of your designs?]*
*If there are several grammars, list them all under this section and what they relate to.*

Production Rules:

    <Non-Terminal> ::= <some output>
    <Non-Terminal> ::= <some output>


### <u>Tokenizers and Parsers</u>

*[Where do you use tokenisers and parsers? How are they built? What are the advantages of the designs?]*

<hr>

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

2. [DataFiles]. Description  ... ... (...)
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...

3. ...
   <br>

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
3. [FB-Auth] Description of the feature (easy)
   * Code: [Class X, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
   * [Class B](../src/path/to/class/file.java#L30-85): methods A, B, C, lines of code: 30 to 85
   * Description of your implementation: ... <br>

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
