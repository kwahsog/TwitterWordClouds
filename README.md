##TwitterWordClouds

Experimental project to combine existing Twitter API and Word Cloud packages to implement some basic functionality to analyse Twitter data. Example methods to write to a SQL db, store the data locally in text files and produce word clouds based on a key search term.

Example uses can be found in the exampleCode package, including a bot that polls periodically and tweets a word cloud. Sample account at:

https://twitter.com/WordCloudBott

##Requirements:
-Configured Twitter4j.properties (not included) in the root directory. Details at: http://twitter4j.org/en/configuration.html

##Improvements:
- Improve testing coverage of SQL methods.
- Work around/solution to rate limits.
- Improve word cloud images.

##Packages used:

https://github.com/yusuke/twitter4j/
https://github.com/kennycason/kumo/

