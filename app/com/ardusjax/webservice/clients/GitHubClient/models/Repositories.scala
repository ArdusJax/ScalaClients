package com.ardusjax.webservice.clients.GitHubClient.models

/**
  * Created by grimlock on 12/22/15.
  */
case class Owner(login:String,
                 id:Int,
                 avatar_url:String,
                 gravatar_id:String,
                 url:String,
                 html_url:String,
                 followers_url:String,
                 following_url:String,
                 gists_url:String,
                 starred_url:String,
                 subscriptions_url:String,
                 organizations_url:String,
                 repos_url:String,
                 events_url:String,
                 received_events_url:String,
                 `type`:String,
                 site_admin:Boolean
                )

case class Repository(id:Int,
                      owner: Owner,
                      name:String,
                      full_name:String,
                      description:String,
                      `private`:Boolean,
                      fork:Boolean,
                      url:String,
                      html_url:String
                     )
case class Repositories(repositories: List[Repository])
