# BMCo-O
Code Smell Detection Method Based on Metrics and Code Smell Co-occurrences 

## Description of some parameters  
***CSDSet***: Code Smell dataset  
***CSTSet***: Code Smell type set  
***f_CS Set***: Set of code-smell-co-occurrence impact factor  
***project***: ASTs of the application  
***CSdeted***: A detected code smell  
***CSdetedSet***: Set of detected code smell  
***CSdetQue***: Queue of code smells to be detected  
***MetricSet***: A set of metrics to characterize code smells  
***PrefilterRuleSet***: Set of rules to pre-filtering code smells  
***MMVAlgorithmSet***: Algorithms for metrics values and mean values in an application project  
***MBVecSet***: Set of Metric boundary vector values  
***CSWDG***: Weighted-directed graph of co-occurrence dependency among code smells  
***$Num_inEwt($CS_{j}$)$***: sum Weight value of in-edges of CS_j  
***PriSet***: Detection priority set of all kinds of code smells  
***F_CS Set***: Initial state set of Code-smell-co-occurrence impactor factor set  
***R_F_CS Set***: realtime state set of Code-smell-co-occurrence impactor factor set  
***Flag_fin***: a finish flag is true if the detection task of a project is completed  
***pool***: a detection pool  
***Par_CS***: Code smell parameter  
***Par_metrics***: Metrics parameter of a code smell  
***DPV***: Vector of the selected detection parameters  

## Metrics used in BMCo-O  
**LOC**: Lines of Code  
**NOF**: Number of Field  
**NOM**: Number of Method  
**MLOC: Method Lines of Code  
**NOP: Number of Parameters  
**NOS: Number of Statements  
**MCC: Method Calling Chain  
**ATFD**: Access To Foreign Data  
**LCOM**: Lack of Cohesion in Methods  
**NOPA**: Number Of Public Attributes  
**NOAM**: Number of Accessor Methods  
**WMC**: Weighted Methods per Class  
**VG**: McCabe Cyclomatic Complexity  
**SIX**: Specialization Index  
**DIT**: Depth of Inheritance Tree  
**TCC**: Tight Class Cohesion  
**NOGV**: Number of Global Variables  
**NORM**: Number of Overridden Methods  
**NPROTM**: Number of Protected Methods  
**NMNOPARAM**: Number of Methods without parameters  
**TD**: Tree Depth  
**TW**: Tree Width  
**MC**: Maximum Number of Children  
**NS**: Number of Nodes  

In the method, we introduce 4 new AST-based metrics to provide richer information about the scale and complexity of code entities. They are as follows.
TreeDepth (TD): The depth of the AST of a code entity. The more statements the code entity has, the more levels and more complex structure its AST. Thus, TD reflects the size and complexity of a code entity and can be used in code smell detection.
TreeWidth (TW): The width of the AST of a code entity. It is similar to TD, the larger the scale of the code entity is, and the more the level nodes of its AST. So TW also can be used in scale-related code smell detection.
Number of Nodes (NS): The total number of the nodes of the AST of a code entity. NS directly reflects the size of a code entity.
Maximum Number of Children (MC): The maximum number of child nodes of the AST of a code entity. MC reflects the number of subordinate parts of the largest part of a code entity. For instance, a large class node contains too many method nodes. A large method node contains too many variable declaration nodes and statement nodes. A large MC indicates that a part of the code entity is too complicated which violates development principles.


# 


It is composed of the source code of BMCo-O, as well as the data for the evaluation. Tips:  

1.baseline_1 is the original data  
2.baseline_2 is the optimized data  
3.extra is the added positive samples from optimization process  
