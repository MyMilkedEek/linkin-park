# linkin-park
*crawlin' in my links*  
*these clicks they never reel*  

## Goal
To find the path between two Wikipedia articles that contains the least amount of clicks.

## Set-up
To pre-fill the dataset, please call 

```
wikipediaRepository.addPageToRepository(articleName)
```

This method will, by default crawl 2 links deep and arrange them in a "wikigame" folder in your user.home folder.

## Short term todo
- Write rudimentary pathfinding algorithm
- Enable mobile version download when a page hasn't been found in the cache
- Add logging and time keeping 
- Disable writing to disk during path finding runs
