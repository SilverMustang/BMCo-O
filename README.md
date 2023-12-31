# BMCo-O
Code Smell Detection Method Based on Metrics and Code Smell Co-occurrences.

## 1.Description of some parameters  
***CSDSet***: Code Smell dataset;     

***CSTSet***: Code Smell type set;    

***f<sub>CS</sub>Set***: Set of code-smell-co-occurrence impact factor;    

***project***: ASTs of the application;    

***CSdeted***: A detected code smell;    

***CSdetedSet***: Set of detected code smell;    

***CSdetQue***: Queue of code smells to be detected;    

***MetricSet***: A set of metrics to characterize code smells;    

***PrefilterRuleSet***: Set of rules to pre-filtering code smells;    

***MMVAlgorithmSet***: Algorithms for metrics values and mean values in an application project;    

***MBVecSet***: Set of Metric boundary vector values;    

***CSWDG***: Weighted-directed graph of co-occurrence dependency among code smells;    

***Num_inEwt(CS<sub>j</sub>)***: sum Weight value of in-edges of $CS_{j}$;  

***PriSet***: Detection priority set of all kinds of code smells;    

***f<sub>CS</sub>Set***: Initial state set of Code-smell-co-occurrence impactor factor set;    

***R<sub>F<sub>CS</sub></sub>Set***: realtime state set of Code-smell-co-occurrence impactor factor set;   

***Flag_fin***: a finish flag is true if the detection task of a project is completed;    

***pool***: a detection pool;    

***Par_CS***: Code smell parameter;    

***Par_metrics***: Metrics parameter of a code smell;    

***DPV***: Vector of the selected detection parameters;  

## 2.Metrics used in BMCo-O  
**Table 1:** **Metrics used in BMCo-O**

| No.    | Metrics description                  | Metrics   |
| ------ | ------------------------------------ | --------- |
| 1      | Lines of Code                        | LOC       |
| 2      | Number of Field                      | NOF       |
| 3      | Number of Method                     | NOM       |
| 4      | Method Lines of Code                 | MLOC      |
| 5      | Number of Parameters                 | NOP       |
| 6      | Number of Statements                 | NOS       |
| 7      | Method Calling Chain                 | MCC       |
| 8      | Access To Foreign Data               | ATFD      |
| 9      | Lack of Cohesion in Methods          | LCOM      |
| 10     | Number Of Public Attributes          | NOPA      |
| 11     | Number of Accessor Methods           | NOAM      |
| 12     | Weighted Methods per Class           | WMC       |
| 13     | McCabe Cyclomatic Complexity         | VG        |
| 14     | Specialization Index                 | SIX       |
| 15     | Depth of Inheritance Tree            | DIT       |
| 16     | Tight Class Cohesion                 | TCC       |
| 17     | Number of Global Variables           | NOGV      |
| 18     | Number of Overridden Methods         | NORM      |
| 19     | Number of Protected Methods          | NPROTM    |
| 20     | Number of Methods without parameters | NMNOPARAM |
| **21** | ***Tree Depth***                     | ***TD***  |
| **22** | ***Tree Width***                     | ***TW***  |
| **23** | **Maximum Number of Children**       | ***MC***  |
| **24** | ***Number of Nodes***                | ***NS***  |

In the method, we introduce 4 new AST-based metrics to provide richer information about the scale and complexity of code entities. They are as follows.  

**TreeDepth**(TD): The depth of the AST of a code entity. The more statements the code entity has, the more levels and more complex structure its AST. Thus, TD reflects the size and complexity of a code entity and can be used in code smell detection.  

**TreeWidth** (TW): The width of the AST of a code entity. It is similar to TD, the larger the scale of the code entity is, and the more the level nodes of its AST. So TW also can be used in scale-related code smell detection.  

**Number of Nodes** (NS): The total number of the nodes of the AST of a code entity. NS directly reflects the size of a code entity.  

**Maximum Number of Children** (MC): The maximum number of child nodes of the AST of a code entity. MC reflects the number of subordinate parts of the largest part of a code entity. For instance, a large class node contains too many method nodes. A large method node contains too many variable declaration nodes and statement nodes. A large MC indicates that a part of the code entity is too complicated which violates development principles.  



## 3.f<sub>CS<sub>i,j</sub></sub>generated by BMCo-O on Palomba dataset

**Table 2:** **f<sub>CS<sub>i,j</sub></sub>generated by BMCo-O on Palomba dataset**

| $CS_{i}$       | $CS_{j}$        | $$f_{CS_{i,j}}$$ |
| -------------- | --------------- | ---------------- |
| Message Chain  | Large Class     | 0.13             |
| Message Chain  | Complex Class   | 0.31             |
| Message Chain  | Refused Bequest | 0.17             |
| Message Chain  | Spaghetti Code  | 0.17             |
| Feature Envy   | Long Method     | 0.18             |
| Spaghetti Code | Long Method     | 0.12             |

## 4.f<sub>CS<sub>i,j</sub></sub> generated by BMCo-O on Palomba dataset

**Table 3:** **f<sub>CS<sub>i,j</sub></sub>generated by BMCo-O on Palomba dataset**

| $CS_{j}$                     | $$f_{CS_{i,j}}$$                               |
| ---------------------------- | ---------------------------------------------- |
| Message Chain                | &empty;                                        |
| Large Class                  | {<Message Chain, 0.13>}                        |
| Complex ClassRefused Bequest | {<Message Chain, 0.31>}{<Message Chain, 0.17>} |
| Feature Envy                 | &empty;                                        |
| Spaghetti Code               | {<Message Chain, 0.17>}                        |
| Long Method                  | {<Feature Envy, 0.18>, <Spaghetti Code,0.12>}  |

In BMCo-O, **Code smell data set** now verifiably uses the Palomba dataset. Because lots of co-occurrence relation data among code smells in Palomba dataset has been investigated [19]. Based on the investigation data [19], there are six code-smell-co-occurrence impactor factors generated by **generating code-smells-co-occurrences impact factors sets**, as shown in Table 2. Then using Formula (2) to calculate the co-occurrence impact factor sets of seven code smells in **code smell type set**, as shown in Table 3.

## 5.Run the code

### （1)***Prerequisites***

Before running the code, make sure you have the following prerequisites:

- Java Development Kit (JDK)
- MySQL database

### （2)***get source code***

- clone source code

  ```
  git clone https://github.com/sjj0403/GSCS.git
  ```

### （3)***Setup***

1)Import the `smelldetector.sql` file into your MySQL database. 

2)Open the project in your preferred Java IDE. 

3)Run the `MainFrame.java` file located at `src/smelldetector/ui/frame/`.

### （4)***Usage***

1)Launch the UI by executing `MainFrame.java`.   

2)In the UI, select the source code project you want to analyze.   

3)Click the "Detect" button to initiate the code smell detection process. 

4)View the results displayed on the UI.

It is composed of the source code of BMCo-O, as well as the data for the evaluation. Tips:

1.baseline_1 is the original data.  

2.baseline_2 is the optimized data.  

3.extra is the added positive samples from optimization process
