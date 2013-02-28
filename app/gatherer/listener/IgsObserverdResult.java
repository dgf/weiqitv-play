package gatherer.listener;

import gatherer.TelnetOutputListener;

public class IgsObserverdResult implements TelnetOutputListener {

    /**
     * <pre>
     * 15 Game 348 I: weslie (18 469 12) vs Yatsugatak (10 491 8)
     * 15 264(B): H6
     * 2
     * 1 8
     * 15 Game 348 I: weslie (18 469 11) vs Yatsugatak (10 491 8)
     * 15 265(W): Pass
     * 2
     * 1 8
     * 15 Game 348 I: weslie (18 469 11) vs Yatsugatak (10 487 7)
     * 15 266(B): E10
     * 2
     * 1 8
     * 15 Game 348 I: weslie (18 468 10) vs Yatsugatak (10 487 7)
     * 15 267(W): G10
     * 2
     * 1 8
     * 15 Game 348 I: weslie (18 468 10) vs Yatsugatak (10 486 6)
     * 15 268(B): G11
     * 2
     * 1 8
     * 15 Game 348 I: weslie (18 468 9) vs Yatsugatak (10 486 6)
     * 15 269(W): Pass
     * 2
     * 1 8
     * 15 Game 348 I: weslie (18 468 9) vs Yatsugatak (10 486 5)
     * 15 270(B): Pass
     * 2
     * 1 8
     * 15 Game 348 I: weslie (18 468 9) vs Yatsugatak (10 486 5)
     * 15 271(W): Pass
     * 2
     * 1 8
     * 22 weslie 1k* 18 468 9 T 6.5 0
     * 22 Yatsugatak 1k* 15 486 5 T 6.5 0
     * 22  0: 4100555555555031444
     * 22  1: 4110555550000001144
     * 22  2: 4100000501111014411
     * 22  3: 4411011050310011101
     * 22  4: 4141110500010141100
     * 22  5: 4441005501011111103
     * 22  6: 4110050001113301001
     * 22  7: 4100005011410000011
     * 22  8: 1111130014111010111
     * 22  9: 0101300111410011414
     * 22 10: 0000301414100314144
     * 22 11: 5550301441055011444
     * 22 12: 5000101111005000144
     * 22 13: 5011111010050501144
     * 22 14: 0141000005555501014
     * 22 15: 3141105550555050014
     * 22 16: 1110005555550550114
     * 22 17: 0100555555505001414
     * 22 18: 0005555555550111141
     * 9 {Game 348: weslie vs Yatsugatak : W 71.5 B 83.0}
     * 1 8
     * </pre>
     */
    @Override
    public boolean notify(String line) {
        return false;
    }

}
