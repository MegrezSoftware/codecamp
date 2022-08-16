package majiang.process

/**
 * 麻将各个阶段
 */
sealed class MajiangProcess {
    /**
     * 发牌阶段
     */
    class DealCardProcess : MajiangProcess() {

    }

    /**
     * 打牌阶段
     */
    class PlayingProcess : MajiangProcess() {

    }

    /**
     * 计分阶段
     */
    class ComputeScoreProcess : MajiangProcess() {

    }
}