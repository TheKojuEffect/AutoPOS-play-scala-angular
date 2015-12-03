package autopos.auth.service

import java.security.MessageDigest

import autopos.auth.UserDetails
import autopos.auth.web.schema.AuthToken
import org.apache.commons.codec.binary.Hex

object TokenProvider {

  val secretKey: String = "JustForTest"
  val tokenValidity: Int = 3600

  def createToken(userDetails: UserDetails) = {
    val expires = System.currentTimeMillis() + 1000L * tokenValidity
    val token = s"${userDetails.username}:$expires:${computeSignature(userDetails, expires)}"
    AuthToken(token, expires)
  }

  def computeSignature(userDetails: UserDetails, expires: Long) = {
    val signature = s"${userDetails.username}:${userDetails.password}:${secretKey}"
    val md5digest = MessageDigest.getInstance("MD5").digest(signature.getBytes)
    Hex encodeHexString md5digest
  }

  def validateToken(authToken: String, userDetails: UserDetails) = {
    val parts = authToken.split(":")
    val expires = parts(1).toLong
    val signature = parts(2)
    expires >= System.currentTimeMillis() && signature == computeSignature(userDetails, expires)
  }

  def usernameFromToken(token: String) =
    token.split(":")(0)

}
