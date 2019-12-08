package github.rank.api

/**
 * instance of Contribution keeps for single contributor number of contributions
 */
case class Contribution(name: String, contributions: Int){
  def +(other: Contribution): Contribution = if(name == other.name){
    Contribution(name, contributions+other.contributions)
  }else{
    throw new IllegalArgumentException(s"Contribution can be sum up for the same contributor $name != ${other.contributions}")
  }
}

case class RateLimit(rateLimit: Int, remainingLimit: Int, limitResetTime:Long)


