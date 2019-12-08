# github-rank
## requirements 
1. use GitHub REST API v3 (​ https://developer.github.com/v3/​ )
1. return a list sorted by the number of contributions made by developer to all repositories for the given organization.
1. respond to a GET request at port 8080 and address /org/{org_name}/contributors
1. respond with JSON, which should be a list of contributors in the following format: { “name”: <contributor_login>, “contributions”: <no_of_contributions> }
1. handle GitHub’s API rate limit restriction using token that can be set as an environment variable of name GH_TOKEN
1. friendly hint: GitHub API returns paginated responses. You should take it into account if you want the result to be accurate for larger organizations.


## requirements imposed by github api
1. api is versioned, version could change
1. hypermedia API, follow rules of hypermedia API -> discover not assume
1. clients must assume redirection's
1. pagination is using standard link header, app should use it two reasons:
    +  next page could be served different node
    +  thanks to link with rel = last, last call to page could be skipped
1. rate limiting answer with http code 403(why not 429???) and rate limits headers which are per public api of github-rank
1. User agent required
1. Conditional requests - doesn't count to rate limits


#3 design decisions
1. use play framework - decision based on popularity analysis 
1. create service api which covers github-rank needs and hide complexity of entire github api
1. provide implementation of service api as v3 api
1. api & implementation of simplified github api could sync or async. Due too simplicity I'm selecting sync approach but I'm totally aware that it could be wrong decision :)  
1. create github-rank service & api which will execute logic
1. rate limits handling
    + github-rank will respond with http code 429 - TOO MANY REQUEST it will reach github limit, with limit reset time, client should take into account this time
    + it is not clear whu is required GH_TOKEN, from the github api perspective is not needed, limits are given in response, so there is an assumption that it is just exercise to make config
    + create "github http request executor" which will trace limit and it will respond with TOO MANY REQUEST   
    + github limit is per public ip, "github http request executor" could be create per one public IP if routing is possible with load balancer
    + "github-rank enterprise version" there could be exposed endpoint to execute request to github and implementation of "github http request executor" to delegate request to another server which allows to create cluster :)
    + http cache must be configured for WSClient, github doesn't count to limit conditional request, thanks to that for large organisations client could repeat calls and finally he will be able to get response
1. in case of unknown organization respond with 404 NOT FOUND
1. github provides pagination due to possible large results, pagination is not in requirements but it seems reasonable to have it : optional
1. tests            
      
    
     
