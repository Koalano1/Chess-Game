package com.cchess.game.game;

import com.cchess.game.game.pieces.Piece;
import com.cchess.game.game.utils.BoardUtils;
import lombok.Data;

@Data
public class Board {

    public static final int ROW = 10;
    public static final int COL = 9;

    private final Piece[][] board;

    private static String defaultBoard =
            "00bCH_01bHO_02bEL_03bAD_04bGE_05bAD_06bEL_07bHO_08bCH_" +
            "10000_11000_12000_13000_14000_15000_16000_17000_18000_" +
            "20000_21bCA_22000_23000_24000_25000_26000_27bCA_28000_" +
            "30bSO_31000_32bSO_33000_34bSO_35000_36bSO_37000_38bSO_" +
            "40000_41000_42000_43000_44000_45000_46000_47000_48000_" +
            "50000_51000_52000_53000_54000_55000_56000_57000_58000_" +
            "60rSO_61000_62rSO_63000_64rSO_65000_66rSO_67000_68rSO_" +
            "70000_71rCA_72000_73000_74000_75000_76000_77rCA_78000_" +
            "80000_81000_82000_83000_84000_85000_86000_87000_88000_" +
            "90rCH_91rHO_92rEL_93rAD_94rGE_95rAD_96rEL_97rHO_98rCH_";

    public Board() {
        this.board = BoardUtils.convertBoardToMatrix(defaultBoard);
    }

}