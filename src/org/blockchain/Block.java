package org.blockchain;

import org.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Fabien on 29/01/2018.
 */
public class Block {

    public String hash;
    public String previousHash;
    private String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>();
    private long timestamp;
    private int nonce;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(previousHash + Long.toString(timestamp) + Integer.toString(nonce) + merkleRoot);
    }

    public void mineBlock(int difficulty){
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDifficulty(difficulty);
        while(!hash.substring(0, difficulty).equals(target)){
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined !! : " + hash);
    }

    public boolean addTransaction(Transaction transaction){
        if (transaction == null) return false;

        if (previousHash != "0"){
            if (transaction.processTransaction() != true){
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
