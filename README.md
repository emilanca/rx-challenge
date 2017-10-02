# Sample RX exercise

## Use case

We have a small microservice that serves GETS for a deal, and deals for merchants.

The operations included are:
  - `GET /deals/{dealId}`
  - `GET /merchant/{merchantId}/deals?onlyEnabled={true|false}&sortBy={title_asc|title_desc|discount_asc|discount_desc}`
 
This service orchestrates data from two other services, for which we have [clients](./src/main/java/org/sample/rx/deals/client).  
The endpoints live in 1 [resource class](./src/main/java/org/sample/rx/deals/resource/DealResource.java).  
The resource obtains the data through 1 [service class](./src/main/java/org/sample/rx/deals/service/DealService.java).

## Tasks
First of all, take a look at the [code](./src/main/java/org/sample/rx/deals), it should be easy to understand what it does (or wants to do).
Looking at the [tests](./src/test/java/org/sample/rx/deals) and the [test data](./src/test/resources) can help in getting the overall idea of the service.  
Then let's try to complete and improve this small service.

  1. Check the *TODO*s all over the code and the tests. The goal is to complete as many of them as possible.
  1. A good place to start is completing the [DealServiceTest](./src/test/java/org/sample/rx/deals/service/DealServiceTest.java) so that is passes the existing tests.
  1. A good continuation could be writing more tests for the `DealServiceTest` covering more use cases and error cases.
  1. The [DealResourceTest](./src/test/java/org/sample/rx/deals/resource/DealResourceTest.java) also needs to be completed.
  1. After covering the existing functionality, the *TODO*s in the code offer lots of points to improve the code.
  1. With each extension of the functionality coming from the *TODO*s, tests should also be provided.
  1. If you feel like going the extra mile, try to improve the code with your best practices/patterns; without changing the resources contract.

## Git
While changing the source code as part of the challenge, don't forget that this is part of a Git repo.  
Follow the best practices that you are used to, including, but not limited to, branches, commits with a well defined scope, meaningful messages, modify `.gitignore` for any _garbage_, etc.

## Submission
To prepare the submission, we need you to prepare a small package that will allow us to asses your exercise.  
We prefer a zipped tarball (tgz) for compatibility.  
A simple way of getting the tgz for us is to run this in your local repo dir:

  1. Copy your repo to a temp folder
```
cp -r /my/repo /temp/folder
```
  1. Remove the .gitignore files 
```
git clean -xid
```
  1. Pack your repo folder
```
tar zcvf file.tgx /temp/folder
```
  1. Test that unpacking works
```
tar zxvf file.tgx
cd <repo> && git status
```

