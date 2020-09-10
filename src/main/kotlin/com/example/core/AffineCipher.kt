package com.example.core

import java.io.File

class  AffineCipher {
   companion object {
       private const val M: Int = 256
       private const val A :Int = 27
       private const val B :Int = 43

       fun encodeFile(path: String){
           val bytes: ByteArray = File(path).readBytes()
           val encodeBytes:ByteArray = ByteArray(bytes.size)
           bytes.forEachIndexed{ i,  byte -> encodeBytes[i] = encode(byte.toInt()).toByte() }
           File(path).writeBytes(encodeBytes)
       }
       fun decodeFile(path: String){
           val bytes: ByteArray = File(path).readBytes()
           val encodeBytes:ByteArray = ByteArray(bytes.size)
           bytes.forEachIndexed{ i,  byte -> encodeBytes[i] = decode(byte.toInt()).toByte() }
           File(path).writeBytes(encodeBytes)
       }

       private fun extendedGcd(a: Int, b: Int): Triple<Int, Int, Int> =
               if (a == 0) Triple(b, 0, 1)
               else {
                   val (g, s, t) = extendedGcd(b % a, a)
                   Triple(g, t - (b / a) * s, s)
               }

       private fun mmi(a: Int, m: Int) = extendedGcd(a, m).let { (g, x, _) ->
           require(g == 1)
           x % m
       }
       private fun encode(m:Int): Int =((A*m+B) % M)
       private fun decode(c:Int): Int = Math.floorMod(mmi(A, M) * (c - B), M)
   }
 }