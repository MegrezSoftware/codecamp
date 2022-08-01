package com.practice.majiang

class PlayerNode(
    val player: IPlayer,
    val next: IPlayer,
) : IPlayer by player