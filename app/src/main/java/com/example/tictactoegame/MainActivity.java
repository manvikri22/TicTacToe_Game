package com.example.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView playeronescore, playertwoscore, playerStatus;
    private Button [] buttons = new Button[9];
    private Button resetgame;

    private  int playerOneScoreCount, playerTwoScoreCount, RountCount;
    boolean activePlayer;


    //p1 -> 0
    //p2 -> 1
    //empty -> 2

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},//row
            {0,3,6}, {1,4,7}, {2,5,8},//columns
            {0,4,8}, {2,4,6}//diagonals
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playeronescore = (TextView) findViewById(R.id.player_onescore);
        playertwoscore = (TextView) findViewById(R.id.player_twoscore);
        playerStatus = (TextView) findViewById(R.id.player_status);

        resetgame = (Button) findViewById(R.id.reset);

        for(int i=0; i < buttons.length; i++)
        {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        RountCount =0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

    }


    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonId = v.getResources().getResourceEntryName(v.getId()); // btn_2
        int gameStatePointer = Integer.parseInt(buttonId.substring(buttonId.length()-1, buttonId.length())); //2

        if(activePlayer){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;

        }else{
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        RountCount++;

        if(checkWinner())
        {
            if(activePlayer==true)
            {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Player 1 won!", Toast.LENGTH_SHORT).show();
                playagain();
            }
            else
            {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Player 2 won!", Toast.LENGTH_SHORT).show();
                playagain();
            }
        }
        else if(RountCount==9)
        {
            playagain();
            Toast.makeText(this,"No winner!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount>playerTwoScoreCount){
            playerStatus.setText("Player One is winning!");
        }
        else if(playerTwoScoreCount>playerOneScoreCount)
        {
            playerStatus.setText("Player Two is winning!");
        }
        else
        {
            playerStatus.setText("");
        }

        resetgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playagain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });

    }
    public  boolean checkWinner(){
        boolean winnerResult = false;
        for(int [] winningPosition : winningPositions){
            if(gameState[winningPosition[0]]== gameState[winningPosition[1]]
                    && gameState[winningPosition[1]]== gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] != 2)
            {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playeronescore.setText(Integer.toString(playerOneScoreCount));
        playertwoscore.setText(Integer.toString(playerTwoScoreCount));

    }

    public void playagain(){
        RountCount = 0;
        activePlayer = true;

        for(int i =0;i< buttons.length;i++)
        {
            gameState[i] =2;
            buttons[i].setText("");
        }
    }
}