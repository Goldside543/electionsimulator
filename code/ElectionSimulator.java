import java.util.Random;
import java.util.Scanner;

class Candidate {
    String name;
    String party;
    long votes;

    Candidate(String name, String party) {
        this.name = name;
        this.party = party;
        this.votes = 0;
    }

    public void addVotes(long additionalVotes) {
        this.votes += additionalVotes;
    }

    public void printStatus() {
        System.out.println(name + " (" + party + ") - Votes: " + votes);
    }
}

class Election {
    Candidate candidate1;
    Candidate candidate2;
    long totalVoters;
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);

    Election() {
        this.totalVoters = 99999999L; // 99,999,999 voters

        // Random candidate names and parties
        this.candidate1 = new Candidate(generateRandomName(), generateRandomParty());
        this.candidate2 = new Candidate(generateRandomName(), generateRandomParty());

        // Initial votes hidden from player; randomize vote counts for suspense
        candidate1.votes = rand.nextInt(100000); // Small starting votes
        candidate2.votes = rand.nextInt(100000);
    }

    public void startElection() {
        // Pre-election campaign phase where players can help a candidate
        preElectionCampaign();

        // Ask the player for their vote
        System.out.println("Welcome to the Election Simulator!");
        System.out.println("You can vote for one of the following candidates:");
        
        System.out.println("1. " + candidate1.name + " (" + candidate1.party + ")");
        System.out.println("2. " + candidate2.name + " (" + candidate2.party + ")");

        System.out.println("\nEnter your vote (1 for " + candidate1.name + ", 2 for " + candidate2.name + "): ");
        int playerVote = scanner.nextInt();

        if (playerVote == 1) {
            candidate1.addVotes(1);
            System.out.println("You voted for " + candidate1.name);
        } else if (playerVote == 2) {
            candidate2.addVotes(1);
            System.out.println("You voted for " + candidate2.name);
        } else {
            System.out.println("Invalid vote!");
            return;
        }

        // Campaign Events that affect the election outcome
        applyCampaignEvents();

        // Gradual vote reveal
        System.out.println("\nCounting votes...");
        gradualVoteReveal();

        // Show final results
        System.out.println("\nElection Results:");
        candidate1.printStatus();
        candidate2.printStatus();

        // Determine the winner
        if (candidate1.votes > candidate2.votes) {
            System.out.println(candidate1.name + " wins the election!");
        } else if (candidate2.votes > candidate1.votes) {
            System.out.println(candidate2.name + " wins the election!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    // Pre-election campaign actions where the player can support a candidate
    private void preElectionCampaign() {
        System.out.println("\nBefore the election, you can help a candidate with some campaign actions.");
        System.out.println("Which candidate do you want to support?");
        System.out.println("1. " + candidate1.name + " (" + candidate1.party + ")");
        System.out.println("2. " + candidate2.name + " (" + candidate2.party + ")");
        
        int supportChoice = scanner.nextInt();
        
        Candidate selectedCandidate = (supportChoice == 1) ? candidate1 : candidate2;

        System.out.println("You chose to support " + selectedCandidate.name + ". Now, choose an action:");
        System.out.println("1. Place yard signs");
        System.out.println("2. Post on social media");
        System.out.println("3. Organize a rally");
        System.out.println("4. Do nothing");

        int action = scanner.nextInt();
        long effect = rand.nextInt(100000); // The impact of the player's action

        if (action == 1) {
            // Yard signs increase visibility and votes
            System.out.println("You placed yard signs for " + selectedCandidate.name + ", gaining " + effect + " votes!");
            selectedCandidate.addVotes(effect);
        } else if (action == 2) {
            // Social media posts reach a broad audience, gaining more votes
            System.out.println("You made a viral social media post for " + selectedCandidate.name + ", gaining " + effect + " votes!");
            selectedCandidate.addVotes(effect);
        } else if (action == 3) {
            // Organizing a rally has a high impact
            System.out.println("You organized a rally for " + selectedCandidate.name + ", gaining " + effect + " votes!");
            selectedCandidate.addVotes(effect);
        } else {
            System.out.println("You decided to do nothing to support any candidate.");
        }
    }

    // Apply random campaign events that may shift votes
    private void applyCampaignEvents() {
        String[] events = {"scandal", "endorsement", "policy proposal", "debate win"};
        String event = events[rand.nextInt(events.length)];
        
        int effect = rand.nextInt(50000); // Effect of event on votes

        switch (event) {
            case "scandal":
                if (rand.nextBoolean()) {
                    candidate1.addVotes(-effect);
                    System.out.println(candidate1.name + " faces a scandal and loses " + effect + " votes!");
                } else {
                    candidate2.addVotes(-effect);
                    System.out.println(candidate2.name + " faces a scandal and loses " + effect + " votes!");
                }
                break;
            case "endorsement":
                if (rand.nextBoolean()) {
                    candidate1.addVotes(effect);
                    System.out.println(candidate1.name + " gets a major endorsement and gains " + effect + " votes!");
                } else {
                    candidate2.addVotes(effect);
                    System.out.println(candidate2.name + " gets a major endorsement and gains " + effect + " votes!");
                }
                break;
            case "policy proposal":
                if (rand.nextBoolean()) {
                    candidate1.addVotes(effect / 2);
                    System.out.println(candidate1.name + " proposes a popular policy and gains " + (effect / 2) + " votes.");
                } else {
                    candidate2.addVotes(effect / 2);
                    System.out.println(candidate2.name + " proposes a popular policy and gains " + (effect / 2) + " votes.");
                }
                break;
            case "debate win":
                if (rand.nextBoolean()) {
                    candidate1.addVotes(effect);
                    System.out.println(candidate1.name + " wins a debate and gains " + effect + " votes!");
                } else {
                    candidate2.addVotes(effect);
                    System.out.println(candidate2.name + " wins a debate and gains " + effect + " votes!");
                }
                break;
        }
    }

    // Reveal votes gradually to build suspense
    private void gradualVoteReveal() {
        long remainingVotes = totalVoters - candidate1.votes - candidate2.votes;

        while (remainingVotes > 0) {
            long batchVotes1 = Math.min(remainingVotes, rand.nextInt(500000));
            long batchVotes2 = Math.min(remainingVotes, rand.nextInt(500000));

            candidate1.addVotes(batchVotes1);
            candidate2.addVotes(batchVotes2);
            remainingVotes -= (batchVotes1 + batchVotes2);

            // Calculate percentages for each candidate
            double percentage1 = (double) candidate1.votes / (candidate1.votes + candidate2.votes) * 100;
            double percentage2 = (double) candidate2.votes / (candidate1.votes + candidate2.votes) * 100;

            // Print current vote counts and percentages
            System.out.printf("\rCurrent Counts: %s - %d (%.1f%%) | %s - %d (%.1f%%)",
                candidate1.name, candidate1.votes, percentage1,
                candidate2.name, candidate2.votes, percentage2);

            try {
                Thread.sleep(500); // Pause for half a second for suspense
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper methods to generate random names and parties
    private String generateRandomName() {
        String[] names = {"Chris Brown", "Jane Smith", "Alex Johnson", "Emily Davis", "Jordan Lee", "Donald Trump", "Ronald Reagan", "Bill Clinton", "John F. Kennedy", "Joe Biden", "Barack Obama", "George H. W. Bush", "George W. Bush", "Jimmy Carter"};
        return names[rand.nextInt(names.length)];
    }

    private String generateRandomParty() {
        String[] parties = {"Democrat", "Republican", "Independent", "Green"};
        return parties[rand.nextInt(parties.length)];
    }
}

public class ElectionSimulator {
    public static void main(String[] args) {
        Election election = new Election();
        election.startElection();
    }
}
