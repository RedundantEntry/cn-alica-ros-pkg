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
// $ANTLR 3.1.1 /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g 2009-05-13 11:19:03

  package de.uni_kassel.cn.planDesigner.translator.parser.antlr;
  
  import org.antlr.runtime.BitSet;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

import de.uni_kassel.cn.planDesigner.translator.parser.antlr.nodes.BlockNode;
import de.uni_kassel.cn.planDesigner.translator.parser.antlr.nodes.KeywordNode;

public class UtilitiesParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "OPERATOR", "L_PARAN", "R_PARAN", "WORD", "NUMBER", "INT", "FLOAT", "WS", "SEPERATOR"
    };
    public static final int WORD=7;
    public static final int WS=11;
    public static final int OPERATOR=4;
    public static final int R_PARAN=6;
    public static final int NUMBER=8;
    public static final int FLOAT=10;
    public static final int INT=9;
    public static final int EOF=-1;
    public static final int L_PARAN=5;
    public static final int SEPERATOR=12;

    // delegates
    // delegators


        public UtilitiesParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public UtilitiesParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return UtilitiesParser.tokenNames; }
    public String getGrammarFileName() { return "/home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g"; }


    public static class utility_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "utility"
    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:18:1: utility : ( util | EOF );
    public final UtilitiesParser.utility_return utility() throws RecognitionException {
        UtilitiesParser.utility_return retval = new UtilitiesParser.utility_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF2=null;
        UtilitiesParser.util_return util1 = null;


        Object EOF2_tree=null;

        try {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:19:3: ( util | EOF )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==L_PARAN||(LA1_0>=WORD && LA1_0<=FLOAT)) ) {
                alt1=1;
            }
            else if ( (LA1_0==EOF) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:19:5: util
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_util_in_utility46);
                    util1=util();

                    state._fsp--;

                    adaptor.addChild(root_0, util1.getTree());

                    }
                    break;
                case 2 :
                    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:20:5: EOF
                    {
                    root_0 = (Object)adaptor.nil();

                    EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_utility52); 
                    EOF2_tree = (Object)adaptor.create(EOF2);
                    adaptor.addChild(root_0, EOF2_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "utility"

    public static class util_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "util"
    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:23:1: util : ding ( OPERATOR ding )* ;
    public final UtilitiesParser.util_return util() throws RecognitionException {
        UtilitiesParser.util_return retval = new UtilitiesParser.util_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OPERATOR4=null;
        UtilitiesParser.ding_return ding3 = null;

        UtilitiesParser.ding_return ding5 = null;


        Object OPERATOR4_tree=null;

        try {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:25:3: ( ding ( OPERATOR ding )* )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:25:5: ding ( OPERATOR ding )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_ding_in_util68);
            ding3=ding();

            state._fsp--;

            adaptor.addChild(root_0, ding3.getTree());
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:25:10: ( OPERATOR ding )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==OPERATOR) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:25:11: OPERATOR ding
            	    {
            	    OPERATOR4=(Token)match(input,OPERATOR,FOLLOW_OPERATOR_in_util71); 
            	    OPERATOR4_tree = (Object)adaptor.create(OPERATOR4);
            	    root_0 = (Object)adaptor.becomeRoot(OPERATOR4_tree, root_0);

            	    pushFollow(FOLLOW_ding_in_util74);
            	    ding5=ding();

            	    state._fsp--;

            	    adaptor.addChild(root_0, ding5.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "util"

    public static class ding_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ding"
    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:28:1: ding : ( val | paran_block );
    public final UtilitiesParser.ding_return ding() throws RecognitionException {
        UtilitiesParser.ding_return retval = new UtilitiesParser.ding_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        UtilitiesParser.val_return val6 = null;

        UtilitiesParser.paran_block_return paran_block7 = null;



        try {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:30:3: ( val | paran_block )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=WORD && LA3_0<=FLOAT)) ) {
                alt3=1;
            }
            else if ( (LA3_0==L_PARAN) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:30:5: val
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_val_in_ding92);
                    val6=val();

                    state._fsp--;

                    adaptor.addChild(root_0, val6.getTree());

                    }
                    break;
                case 2 :
                    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:31:5: paran_block
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_paran_block_in_ding98);
                    paran_block7=paran_block();

                    state._fsp--;

                    adaptor.addChild(root_0, paran_block7.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ding"

    public static class paran_block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "paran_block"
    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:34:1: paran_block : L_PARAN util R_PARAN ;
    public final UtilitiesParser.paran_block_return paran_block() throws RecognitionException {
        UtilitiesParser.paran_block_return retval = new UtilitiesParser.paran_block_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token L_PARAN8=null;
        Token R_PARAN10=null;
        UtilitiesParser.util_return util9 = null;


        Object L_PARAN8_tree=null;
        Object R_PARAN10_tree=null;

        try {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:35:3: ( L_PARAN util R_PARAN )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:35:5: L_PARAN util R_PARAN
            {
            root_0 = (Object)adaptor.nil();

            L_PARAN8=(Token)match(input,L_PARAN,FOLLOW_L_PARAN_in_paran_block113); 
            L_PARAN8_tree = new BlockNode(L_PARAN8) ;
            root_0 = (Object)adaptor.becomeRoot(L_PARAN8_tree, root_0);

            pushFollow(FOLLOW_util_in_paran_block119);
            util9=util();

            state._fsp--;

            adaptor.addChild(root_0, util9.getTree());
            R_PARAN10=(Token)match(input,R_PARAN,FOLLOW_R_PARAN_in_paran_block121); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "paran_block"

    public static class val_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "val"
    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:38:1: val : ( number | keyword ( paran_arg_block )? );
    public final UtilitiesParser.val_return val() throws RecognitionException {
        UtilitiesParser.val_return retval = new UtilitiesParser.val_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        UtilitiesParser.number_return number11 = null;

        UtilitiesParser.keyword_return keyword12 = null;

        UtilitiesParser.paran_arg_block_return paran_arg_block13 = null;



        try {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:39:3: ( number | keyword ( paran_arg_block )? )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0>=NUMBER && LA5_0<=FLOAT)) ) {
                alt5=1;
            }
            else if ( (LA5_0==WORD) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:39:5: number
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_number_in_val135);
                    number11=number();

                    state._fsp--;

                    adaptor.addChild(root_0, number11.getTree());

                    }
                    break;
                case 2 :
                    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:40:5: keyword ( paran_arg_block )?
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_keyword_in_val141);
                    keyword12=keyword();

                    state._fsp--;

                    root_0 = (Object)adaptor.becomeRoot(keyword12.getTree(), root_0);
                    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:40:14: ( paran_arg_block )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==L_PARAN) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:40:15: paran_arg_block
                            {
                            pushFollow(FOLLOW_paran_arg_block_in_val145);
                            paran_arg_block13=paran_arg_block();

                            state._fsp--;

                            adaptor.addChild(root_0, paran_arg_block13.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "val"

    public static class paran_arg_block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "paran_arg_block"
    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:43:1: paran_arg_block : ( L_PARAN ding ( ',' ding )* R_PARAN | L_PARAN R_PARAN );
    public final UtilitiesParser.paran_arg_block_return paran_arg_block() throws RecognitionException {
        UtilitiesParser.paran_arg_block_return retval = new UtilitiesParser.paran_arg_block_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token L_PARAN14=null;
        Token char_literal16=null;
        Token R_PARAN18=null;
        Token L_PARAN19=null;
        Token R_PARAN20=null;
        UtilitiesParser.ding_return ding15 = null;

        UtilitiesParser.ding_return ding17 = null;


        Object L_PARAN14_tree=null;
        Object char_literal16_tree=null;
        Object R_PARAN18_tree=null;
        Object L_PARAN19_tree=null;
        Object R_PARAN20_tree=null;

        try {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:44:3: ( L_PARAN ding ( ',' ding )* R_PARAN | L_PARAN R_PARAN )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==L_PARAN) ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1==R_PARAN) ) {
                    alt7=2;
                }
                else if ( (LA7_1==L_PARAN||(LA7_1>=WORD && LA7_1<=FLOAT)) ) {
                    alt7=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:44:5: L_PARAN ding ( ',' ding )* R_PARAN
                    {
                    root_0 = (Object)adaptor.nil();

                    L_PARAN14=(Token)match(input,L_PARAN,FOLLOW_L_PARAN_in_paran_arg_block160); 
                    pushFollow(FOLLOW_ding_in_paran_arg_block163);
                    ding15=ding();

                    state._fsp--;

                    adaptor.addChild(root_0, ding15.getTree());
                    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:44:19: ( ',' ding )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==SEPERATOR) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:44:20: ',' ding
                    	    {
                    	    char_literal16=(Token)match(input,SEPERATOR,FOLLOW_SEPERATOR_in_paran_arg_block166); 
                    	    pushFollow(FOLLOW_ding_in_paran_arg_block169);
                    	    ding17=ding();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, ding17.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    R_PARAN18=(Token)match(input,R_PARAN,FOLLOW_R_PARAN_in_paran_arg_block173); 

                    }
                    break;
                case 2 :
                    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:45:5: L_PARAN R_PARAN
                    {
                    root_0 = (Object)adaptor.nil();

                    L_PARAN19=(Token)match(input,L_PARAN,FOLLOW_L_PARAN_in_paran_arg_block180); 
                    R_PARAN20=(Token)match(input,R_PARAN,FOLLOW_R_PARAN_in_paran_arg_block183); 

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "paran_arg_block"

    public static class keyword_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "keyword"
    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:48:1: keyword : WORD ;
    public final UtilitiesParser.keyword_return keyword() throws RecognitionException {
        UtilitiesParser.keyword_return retval = new UtilitiesParser.keyword_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token WORD21=null;

        Object WORD21_tree=null;

        try {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:49:3: ( WORD )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:49:5: WORD
            {
            root_0 = (Object)adaptor.nil();

            WORD21=(Token)match(input,WORD,FOLLOW_WORD_in_keyword197); 
            WORD21_tree = new KeywordNode(WORD21) ;
            adaptor.addChild(root_0, WORD21_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "keyword"

    public static class number_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "number"
    // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:52:1: number : ( NUMBER | INT | FLOAT );
    public final UtilitiesParser.number_return number() throws RecognitionException {
        UtilitiesParser.number_return retval = new UtilitiesParser.number_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set22=null;

        Object set22_tree=null;

        try {
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:53:3: ( NUMBER | INT | FLOAT )
            // /home/till/Code/workspace/de.uni_kassel.cn.planDesigner.translator/ANTLR/Utilities.g:
            {
            root_0 = (Object)adaptor.nil();

            set22=(Token)input.LT(1);
            if ( (input.LA(1)>=NUMBER && input.LA(1)<=FLOAT) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set22));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "number"

    // Delegated rules


 

    public static final BitSet FOLLOW_util_in_utility46 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_utility52 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ding_in_util68 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_OPERATOR_in_util71 = new BitSet(new long[]{0x00000000000007A0L});
    public static final BitSet FOLLOW_ding_in_util74 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_val_in_ding92 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_paran_block_in_ding98 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_L_PARAN_in_paran_block113 = new BitSet(new long[]{0x00000000000007A0L});
    public static final BitSet FOLLOW_util_in_paran_block119 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_R_PARAN_in_paran_block121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_number_in_val135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyword_in_val141 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_paran_arg_block_in_val145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_L_PARAN_in_paran_arg_block160 = new BitSet(new long[]{0x00000000000007A0L});
    public static final BitSet FOLLOW_ding_in_paran_arg_block163 = new BitSet(new long[]{0x0000000000001040L});
    public static final BitSet FOLLOW_SEPERATOR_in_paran_arg_block166 = new BitSet(new long[]{0x00000000000007A0L});
    public static final BitSet FOLLOW_ding_in_paran_arg_block169 = new BitSet(new long[]{0x0000000000001040L});
    public static final BitSet FOLLOW_R_PARAN_in_paran_arg_block173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_L_PARAN_in_paran_arg_block180 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_R_PARAN_in_paran_arg_block183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WORD_in_keyword197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}