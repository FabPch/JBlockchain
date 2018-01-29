package org.blockchain;

import com.google.gson.GsonBuilder;
import org.util.StringUtil;

import java.util.ArrayList;

/**
 * Created by Fabien on 29/01/2018.
 */
public class NoobChain {

    public static ArrayList<Block> blockchain = new ArrayList<>();

    public static void main(String[] args){

        Block genesisBlock = new Block("I am the first Block", "0");
        System.out.println("Hash for block 1 : " + genesisBlock.hash);

        Block secondBlock = new Block("Yo im the second block",genesisBlock.hash);
        System.out.println("Hash for block 2 : " + secondBlock.hash);

        Block thirdBlock = new Block("Hey im the third block",secondBlock.hash);
        System.out.println("Hash for block 3 : " + thirdBlock.hash);

        blockchain.add(genesisBlock);
        blockchain.add(secondBlock);
        blockchain.add(thirdBlock);

        String blockshainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockshainJson);
    }

    public static boolean isChainvalid(){
        Block currentBlock;
        Block previousBlock;

        for (int i = 1; i < blockchain.size() - 1; i++){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            if (currentBlock.previousHash.equals(previousBlock.hash)){
                System.out.println("Previous hashes are not equals at position : " + i);
                return false;
            }
            if (currentBlock.hash.equals(currentBlock.calculateHash()){
                System.out.println("Current hashes are not equals at position : " + i);
                return false;
            }
        }
        return true;
    }
}
