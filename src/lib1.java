import java.io.BufferedReader;
import java.io.InputStreamReader;

public class lib1
{
	public static String change(String ss)
	{
		StringBuffer temps = new StringBuffer("");
		int len = ss.length();
		char[] vars = ss.toCharArray();
		for (int i = 0; i < len; i++)
		{
			if (vars[i] != ' ')
				temps.append(vars[i]);
		}
		String s = temps.toString();
		return s;
	}

	public static boolean judge(String ss)
	{
		int len = ss.length();
		char[] vars = ss.toCharArray();
		for (int i = 0; i + 1 < len; i++)
		{
			if (vars[i] == '*' || vars[i] == '+' || vars[i] == '-' || vars[i] == '^')
			{
				if (vars[i + 1] == '*' || vars[i + 1] == '+' || vars[i + 1] == '-' || vars[i + 1] == '^')
				{
					return false;
				}
			}
		}
		for (int i = 0; i < len;)
		{
			if (vars[i] >= '0' && vars[i] <= '9')
			{
				while (++i < len && (vars[i] >= '0' && vars[i] <= '9'))
					;
				if (i >= len)
					return true;
				if (vars[i] >= 'a' && vars[i] <= 'z')
				{
					while (++i < len && (vars[i] >= 'a' && vars[i] <= 'z'))
						;
					if (i >= len)
						return true;
					if (vars[i] == '^')
					{
						i++;
						if (i >= len)
							return false;
						if (vars[i] >= '0' && vars[i] <= '9')
						{
							while (++i < len && (vars[i] >= '0' && vars[i] <= '9') || vars[i] == ' ')
								;
							continue;
						}
						else
						{
							return false;
						}
					}
					else if (vars[i] >= '0' && vars[i] <= '9')
						return false;
					else
						continue;
				}
				else
					continue;
			}
			if (vars[i] >= 'a' && vars[i] <= 'z')
			{
				while (++i < len && (vars[i] >= 'a' && vars[i] <= 'z'))
					;
				if (i >= len)
					return true;
				if (vars[i] == '^')
				{
					i++;
					if (i >= len)
						return false;
					if (vars[i] >= '0' && vars[i] <= '9')
					{
						if (i >= len)
							return true;
						while (++i < len && (vars[i] >= '0' && vars[i] <= '9'))
							;
						continue;
					}
					else
					{
						return false;
					}
				}
				else if (vars[i] >= '0' && vars[i] <= '9')
					return false;
				else
					continue;
			}
			else
				i++;
		}
		return true;
	}

	public static section[] expression(String ss)
	{
		section[] temp = new section[10];
		for (int x = 0; x < temp.length; x++)
		{
			temp[x] = new section();
		}
		int len = ss.length();
		char[] vars = ss.toCharArray();
		int cnt = 0;
		int j = 0;
		for (int i = 0; i < len;)
		{
			cnt++;
			temp[cnt].val = 1;
			temp[cnt].cnt = 0;
			StringBuffer temps = new StringBuffer("");
			while (vars[j] == '-' || vars[j] == '+')
				j++;
			for (; j < len && vars[j] != '-' && vars[j] != '+'; i = j + 1)
			{
				if (vars[j] >= '0' && vars[j] <= '9')
				{
					temp[cnt].val = vars[j] - '0';
					while (++j < len && vars[j] >= '0' && vars[j] <= '9')
					{
						temp[cnt].val *= 10;
						temp[cnt].val += vars[j] - '0';
					}
				}
				else if (vars[j] >= 'a' && vars[j] <= 'z')
				{
					temps = new StringBuffer("");
					temp[cnt].cnt += 1;
					temps.append(vars[j]);
					while (++j < len && vars[j] >= 'a' && vars[j] <= 'z')
						temps.append(vars[j]);
					String s = temps.toString();
					temp[cnt].st[temp[cnt].cnt] = s;
					temp[cnt].pow[temp[cnt].cnt] = 1;
				}
				else if (vars[j] == '^')
				{
					temp[cnt].pow[temp[cnt].cnt] = 0;
					while (++j < len && vars[j] >= '0' && vars[j] <= '9')
					{
						temp[cnt].pow[temp[cnt].cnt] *= 10;
						temp[cnt].pow[temp[cnt].cnt] += vars[j] - '0';
					}
				}
				else if (vars[j] == '*')
					j++;
			}
		}
		return temp;
	}

	public static void main(String[] args) throws Exception
	{
		String expression = "aa*bb*  ccc4+35*aa  *aa *bb   *bb";
		String ex=change(expression);
		judge(ex);
		section[] a = expression(ex);
		BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
		String command = bufr.readLine();
		
		System.out.println("输入公式:" + expression);
		long time = System.currentTimeMillis();
		if (command.contains("!simplify "))
		{
			System.out.println("输入变量值：" + command);
			command = command.replace("!simplify ", "");

			lib1_compute.simplify(command, a);

		}
		else if (command.contains("!d/d "))
		{
			System.out.println("输入求导：" + command);
			command = command.replace("!d/d ", "");
			lib1_compute.d(command, a);
		}
		else
		{
			System.out.println("illegal commad");
		}
		System.out.println("算法执行时间:" + (System.currentTimeMillis() - time) + "毫秒");
	}

}

class section
{
	boolean flag;// judge a section have a var or not
	int val;// the xishu of the section and if the section have no var val means
			// the value of the section
	int cnt;// the number of the var in the section
	String[] st = new String[100];// the var in the section
	int[] pow = new int[100];// the pow of each var in the section
}

//Lab4