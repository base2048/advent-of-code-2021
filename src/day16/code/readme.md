## [AoC 2021 Day 16: Packet Decoder](https://adventofcode.com/2021/day/16)

Unfortunately, this was a boring and tedious challenge. You almost have the feeling that a complex task was put together by hook or by crook, without any relevance to reality and, above all, without any fun. One hat in a box, two boxes with their hats in another hat, and so on and so forth. Garnished with many small additional side problems without any additional value. Just tedious.

Although the basic idea would have been quite nice, Eric and his team should have just oriented themselves a little on the OSI model, introducing new protocols and for the extra challenge, they maybe could have even embedded kind of checksum verification.

About the implementation: nothing earth-shattering. A bit of recursion and for the message readout process a *Decoder* class was written, which reads the message bit by bit with the help of an InputStream and a CharBuffer. This could certainly have been done with a pointer and ordinary substrings as well, but then there would have been no Christmas feeling at all.

---

![AoC 2021 Day 16](../day16--Packet_Decoder.png?raw=true)
