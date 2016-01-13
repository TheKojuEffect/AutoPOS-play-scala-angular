package autopos.item.service

private[service] object ItemCode {

  private val alpha = "3KMEQPINHABTOGCWUVRYZFSXJDL"

  private val base = alpha.length

  private val firstChar = alpha.charAt(0)

  def apply(code: String) = decode(code)

  def apply(id: Int) = encode(id)

  def encode(id: Int) = {
    def enc(in: Int, acc: String): String =
      if (in < 1) acc
      else enc(in / base, alpha(in % base) + acc)

    enc(id, "")
  }

  def decode: PartialFunction[String, Int] = {
    case code: String if code.length == 0 => 0
    case code: String if code.head != firstChar => {
      val in = code.reverse

      def dec(idx: Int, acc: BigInt): Int =
        if (idx == in.length) acc.toInt
        else dec(idx + 1, acc + alpha.indexOf(in(idx)) * BigInt(base).pow(idx))

      dec(0, 0)
    }
  }
}
