# kdcloud

kdcloud is a RESTful webservice that allows to perform Knowledge Discovery and analysis processes on arbitrary data over a cloud platform.

## Features

+ collects arbitrary data sent using standard formats such as CSV, Arff, Json
+ data can be validated according to definible data specifications
+ allows to define an analysis process through workflows, using XML
+ the analysis capabilities can be extended with plugins
+ allows do define using XML a graphical model of the expected output which can be rendered with [jxReport](http://code.google.com/p/jxreport/)

## Modules

+ *kdcloud-webservice* The webservice itself.
+ *kdcloud-engine* The underling engine that executes the analysis. It is intended to be a wrapper of other software such [KNIME](http://knime.org), but currently an embedded engine has been developed.
+ *kdcloud-lib* A java library that can be used to develop a client application.
+ *kdcloud-webclient* Intended to be a webclient using GWT. Not developed anymore.
+ *weka-stripped* A subset of the [weka](http://www.cs.waikato.ac.nz/ml/weka/) API.
