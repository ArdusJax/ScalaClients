package com.ardusjax.webservice.clients.ArtifactoryClient.models

import scala.collection.mutable

/**
  * Created by Christopher O'Brian on 12/23/15.
  */
case class Agent(name: String, version: String)
case class Artifact(name: String)
case class Build(properties: mutable.HashMap[String, String],
                 version: String,
                 name: String,
                 number: String,
                 `type`: String,
                 buildAgent: BuildAgent,
                 agent: Agent,
                 started: String,
                 artifactoryPluginVersion: String,
                 durationMillis: Int,
                 artifactoryPrincipal: String,
                 url: String,
                 vcsRevision: String,
                 vcsUrl: String,
                 licenseControl: LicenseControl,
                 buildRetention: BuildRetention,
                 modules: List[Module],
                 governance: Governance
                 )
case class BuildFile(`type`: String,
                     sha1: String,
                     md5: String)
case class BlackDuckProperties(runChecks: Boolean,
                               appName: String,
                               appVersion: String,
                               reportRecipients: String,
                               scopes: String,
                               includePublishedArtifacts: Boolean,
                               autoCreateMissingComponentRequests: Boolean,
                               autoDiscardStaleComponentRequests: Boolean)
case class BuildAgent(name: String,
                      version: String)
case class BuildRetention(count: Int,
                          minimumBuildDate: String,
                          deleteBuildArtifacts: Boolean,
                          buildNumbersNotToBeDiscarded: List[String])
case class BuildType(name: String)
case class Dependency(id: String,
                      scopes: mutable.Set[String],
                      requiredBy: List[String])
case class Governance(blackDuckProperties: BlackDuckProperties)
case class Issue(key: String,
                 url: String,
                 summary: String,
                 aggregated: Boolean)
case class IssueTracker(name: String,
                        version: String)
case class Issues(tracker: IssueTracker,
                  aggregatedBuildIssues: Boolean,
                  aggregationBuildStatus: String,
                  affectedIssues: mutable.Set[Issue])
case class LicenseControl(runChecks: Boolean,
                          includePublishedArtifacts: Boolean,
                          autoDiscover: Boolean,
                          licenseViolationRecipients: List[String],
                          scopes: List[String])
case class Module(id: String,
                  artifacts: List[Artifact],
                  excludedArtifacts: List[Artifact],
                  dependencies: List[Dependency])