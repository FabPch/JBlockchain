package org.blockchain;

import com.google.gson.GsonBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.util.StringUtil;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fabien on 29/01/2018.
 */
public class NoobChain {

    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>();
    public static float minimumTransaction = 0.1f;
    public static int difficulty = 3;
    public static Wallet walletA;
    public static Wallet walletB;
    public static Transaction genesisTransaction;

    public static void main(String[] args){

        Security.addProvider(new BouncyCastleProvider());

        walletA = new Wallet();
        walletB = new Wallet();

        System.out.println("Private and public keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);

        System.out.println("Is signature verified ?");
        System.out.println(transaction.verifySignature());

//        blockchain.add(new Block("I am the first Block", "0"));
//        System.out.println("try to mine Block 1 ...");
//        blockchain.get(0).mineBlock(difficulty);
//        blockchain.add(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));
//        System.out.println("try to mine Block 2 ...");
//        blockchain.get(1).mineBlock(difficulty);
//        blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
//        System.out.println("try to mine Block 3 ...");
//        blockchain.get(2).mineBlock(difficulty);
//
//        System.out.println("Blockchain is Valid: " + isChainValid());
//        String blockshainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//        System.out.println(blockshainJson);
    }

    public static boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = StringUtil.getDifficulty(difficulty);
        HashMap<String, TransactionOutput> tempUTXOs = new HashMap<>();
        tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

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

    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }
}
