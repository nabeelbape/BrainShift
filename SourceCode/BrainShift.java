import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;

class BrainShift extends JFrame implements KeyListener,ActionListener
{
	JLabel timeLabel,scoreLabel,infoMessage1,infoMessage2,upperString,lowerString;
	JPanel outerPanel,controlPanel,innerPanel1,innerPanel2;
	JButton yes,no;
	Container cp;
	Timer tim;
	char alphabetArray[],randomAlphabet;
	int numberArray[],randomNumber,minutes=1,seconds=59,counter=0,score=0;
	boolean upFlag=false;

	BrainShift(String title)
	{
		super(title);

		//----------Initialization------------

		timeLabel=new JLabel(" Time : 01:59",new ImageIcon("icons/clock 22x22.png"),JLabel.CENTER);
		timeLabel.setFont(new Font("sansserif",Font.PLAIN,22));
		timeLabel.setForeground(Color.YELLOW);

		scoreLabel=new JLabel(" Score : 0",new ImageIcon("icons/star 22x22.png"),JLabel.CENTER);
		scoreLabel.setFont(new Font("sansserif",Font.PLAIN,22));
		scoreLabel.setForeground(Color.RED);

		upperString=new JLabel("",JLabel.CENTER);
		upperString.setFont(new Font("sansseriff",Font.PLAIN,50));
		upperString.setForeground(Color.BLUE);
		lowerString=new JLabel("",JLabel.CENTER);
		lowerString.setFont(new Font("sansseriff",Font.PLAIN,50));
		lowerString.setForeground(Color.BLUE);

		infoMessage1=new JLabel("Is the Number Even?");
		infoMessage2=new JLabel("Is the Letter a Vowel?");

		outerPanel=new JPanel(null);
		outerPanel.setBackground(new Color(130,130,155));

		innerPanel1=new JPanel();
		//innerPanel1.setForeground(Color.GRAY);
		innerPanel2=new JPanel();
		//innerPanel2.setForeground(Color.GRAY);

		controlPanel=new JPanel(null);
		controlPanel.setBackground(new Color(130,130,155));

		yes=new JButton("Yes");
		no=new JButton("No");

		tim=new Timer(1000,this);

		cp=getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180,210,230));

		//---------Initializing Array--------

		alphabetArray=new char[26];
		numberArray=new int[26];

		char firstAlphabet='A';
		for(int i=0;i<26;i++)
		{
			alphabetArray[i]=firstAlphabet++;
			numberArray[i]=i;
		}

		//----------Set Location--------------

		timeLabel.setBounds(85,20,180,40);
		scoreLabel.setBounds(250,20,180,40);

		outerPanel.setBounds(100,70,260,260);
		innerPanel1.setBounds(20,20,220,100);
		innerPanel2.setBounds(20,140,220,100);

		infoMessage1.setBounds(370,120,190,50);
		infoMessage2.setBounds(370,240,190,50);

		controlPanel.setBounds(100,340,260,60);
		no.setBounds(20,10,100,35);
		yes.setBounds(140,10,100,35);


		//---------Adding components----------

		innerPanel1.add(upperString);
		innerPanel2.add(lowerString);

		outerPanel.add(innerPanel1);
		outerPanel.add(innerPanel2);

		controlPanel.add(no);
		controlPanel.add(yes);

		cp.add(timeLabel);
		cp.add(scoreLabel);
		cp.add(outerPanel);
		cp.add(controlPanel);
		cp.add(infoMessage1);
		cp.add(infoMessage2);

		outerPanel.addKeyListener(this);
		yes.addKeyListener(this);
		no.addKeyListener(this);

		yes.addActionListener(this);
		no.addActionListener(this);

		//tim.addActionListener(this);

		setBounds(150,150,580,500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JOptionPane.showMessageDialog(this,"Let the game Begin!","Start",JOptionPane.INFORMATION_MESSAGE);
		setString();
		tim.start();

	}

	//-------------------System Methods-----------------

	public String generateString()
	{
		int anyRandomValue;
		String finalString;

		randomNumber=numberArray[((int)(Math.random()*26))];
		randomAlphabet=alphabetArray[((int)(Math.random()*26))];

		anyRandomValue=(int)(Math.random()*25);
		//System.out.println(anyRandomValue);

		if(anyRandomValue<75)
			finalString=""+randomAlphabet+" "+randomNumber;
		else
			finalString=""+randomNumber+" "+randomAlphabet;

		return(finalString);

	}

	public void setString()
	{
		counter=0;

		String displayString;
		displayString=generateString();

		if(((int)(Math.random()*100))<50)
		{
			upFlag=true;
			upperString.setText(displayString);
			lowerString.setText("");
		}
		else
		{
			upFlag=false;
			lowerString.setText(displayString);
			upperString.setText("");
		}
	}

	public void getTime()
	{
		String time;
		int x[],y[];
		x=new int[2];
		y=new int[2];

		if(minutes==0)
		{
			tim.stop();
			JOptionPane.showMessageDialog(this,"Your Score is "+score,"Time Out",JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}

		else
		{
			if(seconds==0)
			{
				minutes--;
				seconds=59;
			}
			else
				seconds--;

			x[0]=minutes/10;
			x[1]=minutes%10;
			y[0]=seconds/10;
			y[1]=seconds%10;

			//System.out.println("x[0]:"+x[0]+" x[1]:"+x[1]+" y[0]:"+y[0]+"y[1]:"+y[1]);

			time="Time : "+x[0]+""+x[1]+":"+y[0]+""+y[1];
			timeLabel.setText(time);

		}

	}

	public boolean checkAnswer()
	{
		if(upFlag)
		{
			if(randomNumber%2==0)
				return(true);
			else
				return(false);
		}

		else
		{
			if(randomAlphabet=='A' ||
				randomAlphabet=='E' ||
				randomAlphabet=='I' ||
				randomAlphabet=='O' ||
				randomAlphabet=='U')

				return(true);
			else
				return(false);
		}
	}

	//-----------------Keyboard Events------------------

	public void keyPressed(KeyEvent ke)
	{
		if(ke.getKeyCode()==KeyEvent.VK_LEFT)
		{
			//System.out.println("left");

			if(checkAnswer())
				score-=10;
			else
				score+=10;

			setString();
		}

		else if(ke.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			//System.out.println("right");

			if(checkAnswer())
				score+=10;
			else
				score-=10;

			setString();
		}

		scoreLabel.setText("Score : "+score);
	}

	public void keyReleased(KeyEvent ke)
	{
	}

	public void keyTyped(KeyEvent ke)
	{
	}

	//----------------Action Events--------------------

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==tim)
		{
			getTime();

			if(counter++== 2)
			{
				setString();
				score-=10;
				counter=0;
			}
		}

		if(ae.getSource()==yes)
		{
			//System.out.println("action yes");
			if(checkAnswer())
				score+=10;
			else
				score-=10;

			setString();

		}

		if(ae.getSource()==no)
		{
			//System.out.println("action no");
			if(checkAnswer())
				score-=10;
			else
				score+=10;

			setString();
		}
		scoreLabel.setText("Score : "+score);
	}

	//----------------Main Method-----------------------

	public static void main(String args[])
	{
		new BrainShift("BrainShift");
	}
}