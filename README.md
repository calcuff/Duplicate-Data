## Editor's note
Migrated to [https://github.com/calcuff/DupeData] to create a Maven managed project. No new updates will
be posted here as of this commit. (4/7/19) See new link for working copy.

## Duplicate-Data
This project is designed to parse in a CSV file containing different data entries. This lightweight java
application will identify possible duplicate entries in the data set. I implemented the Metaphone phonetic
and Levenshtein character distance algorithms from the Apache library to compare entries for similarity.

One of the most important decisions to be made in this exercise is to decide on how to compare the data, and
what tests will indicate duplicates. We must find unique keys and specific fields that can be used to indicate
possible duplicates. For this particular set of data I saw first and last names and emails as the most telling
of duplicate entries. As there can not be any 2 people with the same email, unless they share it or are a
spammer, an email match would be a high indicator of repetitive data. Since emails are usually a combination
of characters, words, numbers and a service, a character distance calculation would be more applicable than any
type of phonetic comparison. Thus my program searches for entries with a Levenshtein distance of 1 or less
characters to allow for small typos or misspellings. This test alone was able to identify all the numerically
marked duplicates in the data set, but often times people give different emails when giving their information
so another test should be implemented for validity.

While people may share first and last names, for this data set it proves to be usable as an identifier. What
played a major role in my decision making process was collection of a possible duplicate entry over a period
of time. People could be at a different company, have different email, have moved to a new address, or have
a different phone number. Whereas full name changes are less common I saw this as a good opportunity to
leverage the Metaphone phonetic comparison algorithm. If the first and last name of one entry “sounds like”
both the first and last name of another entry, the test would deem them potential duplicates. This test alone
also successfully identified the numerically marked duplicates, as it allows for some misspellings of names.
It is possible to set a smaller maximum length of a string for the Metaphone comparison so that it could be
possible to compare nicknames and full names across the threshold (i.e., John Smith = Jonathan Smith). For
this dataset it was not necessary and limiting the maximum length would have done more harm than good.

My program currently requires only one of the tests to pass in order for a record to be deemed a potential
duplicate, however I can see scenarios where expansion of the search criteria could be useful if too many
false positives start to filter into our results.
