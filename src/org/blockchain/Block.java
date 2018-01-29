package org.blockchain;

import org.util.StringUtil;

import java.util.Date;

/**
 * Created by Fabien on 29/01/2018.
 */
public class Block {

    public String hash;
    public String previousHash;
    private String data;
    private long timestamp;
    private int nonce;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(previousHash + Long.toString(this.timestamp) + Integer.toString(nonce) + this.data);
    }

    public void mineBlock(int difficulty){
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring(0, difficulty).equals(target)){
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined !! : " + hash);
    }
}
