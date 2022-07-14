package com.yuanfudao.megrez.app.codecamp.first

class WhichToWin(
    private val circle: Candidate?,
    private val line: Candidate?,
    private val character: Candidate?
) {

    init {
        initAnlayzing()
    }

    private fun initAnlayzing() {
        circle?.let {
            analyzeCandidate(circle)
        }
        line?.let {
            analyzeCandidate(line)
        }
        character?.let {
            analyzeCandidate(character)
        }
    }

    private fun analyzeCandidate(candidate: Candidate) {
//        val canTake = HashMap<Int, Int>()
//        candidate.countMap.forEach { (k, v) ->
//            canTake[k] = 4 - v
//        }
//        canTake[candidate.remainCard] = canTake.getOrDefault(candidate.remainCard, 0) + 1
        val missing = HashMap<Int, Int>()
        var onlyTwins: Match? = null
        var cardCount = 0
        candidate.match.forEach { match ->
            when (match) {
                is Match.Next2Next -> missing[match.missing] =
                    missing.getOrDefault(match.missing, 0) + 1
                is Match.TwoSibling -> missing[match.first.mahjong.num] =
                    missing.getOrDefault(match.first.mahjong.num, 0) + 1
                is Match.SingleOne -> missing[match.card.mahjong.num] =
                    missing.getOrDefault(match.card.mahjong.num, 0) + 1
                is Match.Doubles -> {
                    if (onlyTwins == null) {
                        onlyTwins = match
                    } else {
                        missing[match.card.mahjong.num] =
                            missing.getOrDefault(match.card.mahjong.num, 0) + 1
                    }
                }
                else -> {
                }
            }
            cardCount++
        }
        println(
            "检查：${candidate.match}, 听牌：${missing.toList()}，missingMap: $missing, "
        )

    }

    private fun requireCandidateQualify(candidate: Candidate) {

    }

    fun getWhichToWinList() {

    }
}