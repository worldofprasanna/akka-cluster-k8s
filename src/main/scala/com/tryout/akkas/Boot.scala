package com.tryout.akkas

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.{InitialStateAsEvents, MemberEvent, MemberJoined, MemberRemoved, MemberUp, UnreachableMember}
import com.typesafe.config.ConfigFactory

object Boot extends App {
  println("----------------------- Welcome to Akka & Kubernetes POC -----------------------------")

  def startCluster(hostname: String, port: String, seedNodes: String): Unit = {
        val config = ConfigFactory.parseString(
          s"""
             |akka.remote.artery.canonical.hostname = $hostname
             |akka.remote.artery.canonical.port = $port
             |akka.cluster.seed-nodes = $seedNodes
          """.stripMargin
        ).withFallback(ConfigFactory.load("application.conf"))
        val system = ActorSystem("MyAkkaCluster", config)
        system.actorOf(Props[ClusterSubscriber], "clusterSubscriber")
  }
  val port = sys.env.getOrElse("PORT", "2551")
  val hostname = sys.env.getOrElse("HOSTNAME", "localhost")
  val seedNodeIPs = sys.env.getOrElse("SEED_NODES", "akka0.akka.seed")
  val seedNodes = seedNodeIPs.split(",").map{ip => s""""akka://MyAkkaCluster@${ip.trim}:2551""""}.mkString(",")
  println(s"Hostname: $hostname, port: $port, seedNodes: $seedNodes")

  startCluster(hostname, port, s"[$seedNodes]")
}

class ClusterSubscriber extends Actor with ActorLogging {
  val cluster = Cluster(context.system)

  override def preStart(): Unit = {
    cluster.subscribe(
      self,
      initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent],
      classOf[UnreachableMember]
    )
  }

  override def postStop(): Unit = cluster.unsubscribe(self)

  override def receive: Receive = {
    case MemberJoined(member) =>
      log.info(s"New member in town: $member")
    case MemberUp(member) =>
      log.info(s"Member is up: ${member.address}")
    case MemberUp(member) =>
      log.info(s"Welcome our new member: $member")
    case MemberRemoved(member, previousStatus) =>
      log.info(s"Poor member: ${member.address} it was removed from $previousStatus")
    case UnreachableMember(member) =>
      log.info(s"Uh oh, member ${member.address} is unreachable")
    case m: MemberEvent =>
      log.info(s"Another member event ${m}")
  }
}
