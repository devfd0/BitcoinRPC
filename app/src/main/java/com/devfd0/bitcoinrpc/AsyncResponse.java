package com.devfd0.bitcoinrpc;

/**
 * Created by fd0 on 20/02/17.
 https://gist.github.com/ed-george/8097940a2b2a55d036c5
 */
interface AsyncResponse {

        void onProcessFinish(String result, int id);


}
