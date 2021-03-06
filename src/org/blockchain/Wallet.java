package org.blockchain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.*;

/**
 * Created by Fabien on 04/02/2018.
 */
public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public HashMap<String, TransactionOutput> UTXOs = new HashMap<>();

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair(){
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public float getBalance(){
        float total = 0;

        for (Map.Entry<String, TransactionOutput> i : NoobChain.UTXOs.entrySet()){
            if (i.getValue().isMine(publicKey)){
                UTXOs.put(i.getKey(), i.getValue());
                total += i.getValue().value;
            }
        }

        return total;
    }

    public Transaction sendFunds(PublicKey recipient, float value){
        if (getBalance() < value){
            System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<>();

        float total = 0;
        for (Map.Entry<String, TransactionOutput> i : UTXOs.entrySet()){
            total += i.getValue().value;
            inputs.add(new TransactionInput(i.getKey()));
            if (total > value) break;
        }

        Transaction newTransaction = new Transaction(publicKey, recipient, value, inputs);
        newTransaction.generateSignature(privateKey);

        for (TransactionInput i : inputs){
            UTXOs.remove(i.transactionOutputId);
        }

        return newTransaction;
    }


}
