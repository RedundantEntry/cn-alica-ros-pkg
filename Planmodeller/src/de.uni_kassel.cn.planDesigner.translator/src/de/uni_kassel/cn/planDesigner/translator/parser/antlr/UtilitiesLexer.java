// Copyright 2009 Distributed Systems Group, University of Kassel
// This program is distributed under the GNU Lesser General Public License (LGPL).
//
// This file is part of the Carpe Noctem Software Framework.
//
//    The Carpe Noctem Software Framework is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    The Carpe Noctem Software Framework is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
// $ANTLR 3.1.1 /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g 2009-05-13 11:19:04

  package de.uni_kassel.cn.planDesigner.translator.parser.antlr;


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class UtilitiesLexer extends Lexer {
    public static final int WORD=7;
    public static final int WS=11;
    public static final int OPERATOR=4;
    public static final int R_PARAN=6;
    public static final int NUMBER=8;
    public static final int INT=9;
    public static final int FLOAT=10;
    public static final int L_PARAN=5;
    public static final int EOF=-1;
    public static final int SEPERATOR=12;

    // delegates
    // delegators

    public UtilitiesLexer() {;} 
    public UtilitiesLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public UtilitiesLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g"; }

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:58:7: ( '0' .. '9' )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:58:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:60:4: ( ( NUMBER )+ )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:60:6: ( NUMBER )+
            {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:60:6: ( NUMBER )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:60:7: NUMBER
            	    {
            	    mNUMBER(); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:62:6: ( INT '.' INT )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:62:8: INT '.' INT
            {
            mINT(); 
            match('.'); 
            mINT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "WORD"
    public final void mWORD() throws RecognitionException {
        try {
            int _type = WORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:64:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | NUMBER )+ )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:64:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | NUMBER )+
            {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:64:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | NUMBER )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WORD"

    // $ANTLR start "L_PARAN"
    public final void mL_PARAN() throws RecognitionException {
        try {
            int _type = L_PARAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:66:8: ( '(' )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:66:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "L_PARAN"

    // $ANTLR start "R_PARAN"
    public final void mR_PARAN() throws RecognitionException {
        try {
            int _type = R_PARAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:68:8: ( ')' )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:68:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "R_PARAN"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:70:3: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:70:5: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:70:5: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\t' && LA3_0<='\n')||LA3_0=='\r'||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "OPERATOR"
    public final void mOPERATOR() throws RecognitionException {
        try {
            int _type = OPERATOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:72:9: ( '-' | '+' | '*' | '/' )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:
            {
            if ( (input.LA(1)>='*' && input.LA(1)<='+')||input.LA(1)=='-'||input.LA(1)=='/' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OPERATOR"

    // $ANTLR start "SEPERATOR"
    public final void mSEPERATOR() throws RecognitionException {
        try {
            int _type = SEPERATOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:74:10: ( ',' )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:74:12: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEPERATOR"

    public void mTokens() throws RecognitionException {
        // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:1:8: ( NUMBER | INT | FLOAT | WORD | L_PARAN | R_PARAN | WS | OPERATOR | SEPERATOR )
        int alt4=9;
        alt4 = dfa4.predict(input);
        switch (alt4) {
            case 1 :
                // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:1:10: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 2 :
                // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:1:17: INT
                {
                mINT(); 

                }
                break;
            case 3 :
                // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:1:21: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 4 :
                // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:1:27: WORD
                {
                mWORD(); 

                }
                break;
            case 5 :
                // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:1:32: L_PARAN
                {
                mL_PARAN(); 

                }
                break;
            case 6 :
                // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:1:40: R_PARAN
                {
                mR_PARAN(); 

                }
                break;
            case 7 :
                // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:1:48: WS
                {
                mWS(); 

                }
                break;
            case 8 :
                // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:1:51: OPERATOR
                {
                mOPERATOR(); 

                }
                break;
            case 9 :
                // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:1:60: SEPERATOR
                {
                mSEPERATOR(); 

                }
                break;

        }

    }


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\1\uffff\1\10\10\uffff\1\13\1\uffff";
    static final String DFA4_eofS =
        "\14\uffff";
    static final String DFA4_minS =
        "\1\11\1\56\10\uffff\1\56\1\uffff";
    static final String DFA4_maxS =
        "\2\172\10\uffff\1\172\1\uffff";
    static final String DFA4_acceptS =
        "\2\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\1\1\3\1\uffff\1\2";
    static final String DFA4_specialS =
        "\14\uffff}>";
    static final String[] DFA4_transitionS = {
            "\2\5\2\uffff\1\5\22\uffff\1\5\7\uffff\1\3\1\4\2\6\1\7\1\6\1"+
            "\uffff\1\6\12\1\7\uffff\32\2\4\uffff\1\2\1\uffff\32\2",
            "\1\11\1\uffff\12\12\7\uffff\32\2\4\uffff\1\2\1\uffff\32\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\11\1\uffff\12\12\7\uffff\32\2\4\uffff\1\2\1\uffff\32\2",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( NUMBER | INT | FLOAT | WORD | L_PARAN | R_PARAN | WS | OPERATOR | SEPERATOR );";
        }
    }
 

}