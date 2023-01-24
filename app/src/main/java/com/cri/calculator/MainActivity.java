package com.cri.calculator;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.CardView;
public class MainActivity extends AppCompatActivity {

	ImageView btResult;
	private EditText screen;
	TextView ans;
	private  boolean operator, hasdot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		// Screen
		ans = findViewById(R.id.activity_mainTextView_ans);
		screen = findViewById(R.id.activity_mainEditText);
		screen.setFocusable(false);
		setupnumber();
		setUpOpCL();

	}
	private void setupnumber() {
		setOnNumberButtonClickListener(R.id.num_0, "0");
		setOnNumberButtonClickListener(R.id.num_1, "1");
		setOnNumberButtonClickListener(R.id.num_2, "2");
		setOnNumberButtonClickListener(R.id.num_3, "3");
		setOnNumberButtonClickListener(R.id.num_4, "4");
		setOnNumberButtonClickListener(R.id.num_5, "5");
		setOnNumberButtonClickListener(R.id.num_6, "6");
		setOnNumberButtonClickListener(R.id.num_7, "7");
		setOnNumberButtonClickListener(R.id.num_8, "8");
		setOnNumberButtonClickListener(R.id.num_9, "9");
	}
	private void setOnNumberButtonClickListener(int id, final String string) {
		findViewById(id).setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					operator = false;
					if (screen.getText().toString().compareToIgnoreCase("Infinity") == 0 ||
						(screen.getText().length() == 1 && screen.getText().charAt(0) == '0'))
						screen.setText("");
					screen.setText(screen.getText() + string);
					movecarot();
				}
			});
	}
	private void setOnOperatiomClickListener(int id, final String string) {
		findViewById(id).setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					if (!TextUtils.isEmpty(screen.getText().toString()) && screen.getText().toString().compareToIgnoreCase("Infinity") != 0) {
						if (operator) {
							screen.setText(screen.getText().subSequence(0, screen.getText().length() - 3));
						}
						screen.setText(screen.getText().toString() + string);
						operator = true; 
						hasdot = false;
						movecarot();
					}
				}
			});

	}
	private void setUpOpCL() {
		setOnOperatiomClickListener(R.id.mainImageView_plus, " + ");
		setOnOperatiomClickListener(R.id.mainImageView_subs, " - ");
		setOnOperatiomClickListener(R.id.mainImageView_div, " / ");
		setOnOperatiomClickListener(R.id.mainImageView_multi, " * ");
		//setOnOperatiomClickListener(R.id.btplus," + ");
		//setOnOperatiomClickListener(R.id.btplus," + ");
	}
	public void sqrt(View v) {
		if (!TextUtils.isEmpty(screen.getText().toString()) &&
			!operator && screen.getText().toString().compareToIgnoreCase("Infinity") != 0) {
			String expression = getLastDigitedNumber();
			String answer = Double.toString(Math.sqrt(Double.parseDouble(expression)));
			hasdot = doesItHasADot(answer);
			if (answer.charAt(answer.length() - 1) == '0' && answer.charAt(answer.length() - 2) == '.') {
				answer = answer.substring(0, answer.length() - 2);
				hasdot = false;
			}
			screen.setText(screen.getText());
			//ans.setText(screen.getText() +
			ans.setText(
				answer);

			movecarot();
		}
	}

	public void powto2(View v) {
		if (!TextUtils.isEmpty(screen.getText().toString())
			&& !operator && screen.getText().toString().compareToIgnoreCase("Infinity") != 0) {
			String expression = getLastDigitedNumber();
			String answer = Double.toString(Math.pow(Double.parseDouble(expression), 2));
			hasdot = doesItHasADot(answer);
			if (answer.charAt(answer.length() - 1) == '0' && answer.charAt(answer.length() - 2) == '.') {
				answer = answer.substring(0, answer.length() - 2);
				hasdot = false;
			}
			//ans.setText(screen.getText() + 
			ans.setText(
				answer);
			screen.setText(screen.getText());
			movecarot();
		}
	}

	public void fat(View v) {
		if (!TextUtils.isEmpty(screen.getText().toString())
			&& !operator && screen.getText().toString().compareToIgnoreCase("Infinity") != 0) {
			String expression = getLastDigitedNumber();
			long toInt = (long)Double.parseDouble(expression);
			if (toInt <= 20) {
				long answer = 1;
				for (int i = 2; i <= toInt; i++) {
					answer *= i;
				}
				//ans.setText(screen.getText() +
				ans.setText(Long.toString(answer));
			} else {
				//ans.setText(screen.getText() + 
				ans.setText("0");
			}
			hasdot = false;
			movecarot();
		}
	}
	// Other operations
	public void plusminus(View v) {
		if (!TextUtils.isEmpty(screen.getText().toString())
			&& !operator && screen.getText().toString().compareToIgnoreCase("Infinity") != 0) {
			String expression = getLastDigitedNumber2();
			String d = Double.toString(Double.parseDouble(expression) * -1);

			hasdot = doesItHasADot(d);
			if (d.charAt(d.length() - 1) == '0' && d.charAt(d.length() - 2) == '.') {
				d = d.substring(0, d.length() - 2);
				hasdot = false;
			}
			screen.setText(screen.getText() + d);
			movecarot();
		}
	}

	public void dot(View v) {
		if (!hasdot && !operator
			&& !TextUtils.isEmpty(screen.getText().toString())
			&& screen.getText().toString().compareToIgnoreCase("Infinity") != 0) {
			screen.setText(screen.getText() + ".");

			hasdot = true;
			movecarot();
		}
	}

	public void clear(View v) {
		screen.setText("");
		ans.setText("");
		hasdot = operator = false;
		movecarot();
	}

	public void clearcurrent(View v) {
		if (!TextUtils.isEmpty(screen.getText().toString()) && !operator) {
			getLastDigitedNumber2();
			hasdot = false;
			operator = true;
			ans.setText("");
			movecarot();
		}
	}

	public void erase(View v) {
		char lastChar = ' ';
		if (!TextUtils.isEmpty(screen.getText().toString())) {
			if (screen.getText().toString().compareToIgnoreCase("Infinity") == 0) {
				screen.setText("");
			} else {
				if (operator) {
					screen.setText(screen.getText().subSequence(0, screen.getText().length() - 3));
					operator = false; 
				} else {
					lastChar = screen.getText().charAt(screen.getText().length() - 1);
					screen.setText(screen.getText().subSequence(0, screen.getText().length() - 1));
				}
				if (screen.getText().length() > 0) {
					char currentLastOne = screen.getText().charAt(screen.getText().length() - 1);
					if (currentLastOne == '.') {
						screen.setText(screen.getText().subSequence(0, screen.getText().length() - 1));
						hasdot = false;
					} else if (currentLastOne == ' ') {
						operator = true;
					} else if (currentLastOne == '-') {
						screen.setText(screen.getText().subSequence(0, screen.getText().length() - 1));
					}
				}
			}

			if (lastChar == '.')
				hasdot = false;
			movecarot();
		}
		ans.setText("");
	}


	public void result(View v) {
		if (!TextUtils.isEmpty(screen.getText().toString())
			&& !operator && screen.getText().toString().compareToIgnoreCase("Infinity") != 0) {
			List<Double> number = new ArrayList<Double>();
			List<Character> operators = new ArrayList<Character>();
			String expression = screen.getText().toString();
			String value = ""; 
			expression += " ";
			for (int i = 0; i < expression.length(); i++) {
				if ((expression.charAt(i) != ' ' && !isAnOperatorSign(expression.charAt(i))) ||
					(isAnOperatorSign(expression.charAt(i)) && expression.charAt(i + 1) != ' ')) {
					value += expression.charAt(i);
				} else {

					if (value != "") {

						number.add(Double.parseDouble(value));
						value = ""; 
					} else {
						if (isAnOperatorSign(expression.charAt(i))) {
							operators.add((expression.charAt(i)));
						}
					}
				}
			}
			if (operators.size() > 0) {
				String resp = calculation(number, operators).toString();
				hasdot = doesItHasADot(resp);
				if (resp.charAt(resp.length() - 1) == '0' && resp.charAt(resp.length() - 2) == '.') {
					resp = resp.substring(0, resp.length() - 2);
					hasdot = false;
				}
				ans.setText(resp);
				operator = false;
				movecarot();
			}
		}

	}

	// Extra functions
	public Double calculation(List<Double>number, List<Character>op) {
		Double resp = 0.0;
		while (number.size() > 1) {
			int i;
			boolean found = false;
			for (i = 0; i < op.size(); i++) {
				if (op.get(i) == '/' || op.get(i) == '*') {
					found = true;
					break;
				}
			}
			if (!found) {
				i = 0;
			}
			resp = doMath(number.get(i), number.get(i + 1), op.get(i));
			number.set(i + 1, resp);
			number.remove(i);

			op.remove(i);
		}

		return resp;
	}

	public String getLastDigitedNumber2() {
		String expression = "";
		int i = 0;
		for (i = screen.getText().length() - 1; i >= 0; i--) {
			if (screen.getText().charAt(i) != ' ') {
				expression += screen.getText().charAt(i);
			} else {
				break;
			}
		}
		screen.setText(screen.getText().subSequence(0, i + 1));
		String finalres = "";
		for (i = expression.length() - 1; i >= 0; i--)
			finalres += expression.charAt(i);

		return finalres;
	}

	public String getLastDigitedNumber() {
		String expression = "";
		int i = 0;
		for (i = screen.getText().length() - 1; i >= 0; i--) {
			if (screen.getText().charAt(i) != ' ') {
				expression += screen.getText().charAt(i);
			} else {
				break;
			}
		}
		//screen.setText(screen.getText().subSequence(0, i + 1));
		String finalres = "";
		for (i = expression.length() - 1; i >= 0; i--)
			finalres += expression.charAt(i);

		return finalres;
	}


	public boolean doesItHasADot(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '.') {
				return true;
			}
		}
		return false;
	}

	// realizar matematica
	public double doMath(Double n1, double n2, char op) {
		double r = 0;
		switch (op) {
			case '+':
				r = n1 + n2;
				break;
			case '-':
				r = n1 - n2;
				break;
			case '/':
				r = n1 / n2;
				break;
			case '*':
				r = n1 * n2;
				break;
		}
		return r;
	}
	public boolean isAnOperatorSign(char c) {
		return (c == '+' || c == '-' || c == '/' || c == '*');
	}
	public void movecarot() {
		screen.setSelection(screen.getText().length());
	}
}
