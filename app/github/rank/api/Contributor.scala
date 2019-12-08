package github.rank.api

case class Contributor(name: String, contributions: Int){
}

case class RateLimit(rateLimit: Int, remainingLimit: Int, limitResetTime:Long)


