package org.blockchain;

import com.google.gson.GsonBuilder;
import org.util.StringUtil;

import java.util.ArrayList;

/**
 * Created by Fabien on 29/01/2018.
 */
public class NoobChain {

    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 5;

    public static void main(String[] args){

        blockchain.add(new Block("I am the first Block", "0"));
        System.out.println("try to mine Block 1 ...");
        blockchain.get(0).mineBlock(difficulty);
        blockchain.add(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));
        System.out.println("try to mine Block 2 ...");
        blockchain.get(1).mineBlock(difficulty);
        blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
        System.out.println("try to mine Block 3 ...");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("Blockchain is Valid: " + isChainValid());
        String blockshainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockshainJson);
    }

    public static boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchain.size() - 1; i++){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            if (!currentBlock.previousHash.equals(previousBlock.hash)){
                System.out.println("Previous hashes are not equals at position : " + i);
                return false;
            }
            if (!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("Current hashes are not equals at position : " + i);
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)){
                System.out.println("The Block number " + i + " has not been mined");
                return false;
            }
        }
        return true;
    }
}
