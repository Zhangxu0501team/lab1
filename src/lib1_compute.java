import java.util.HashMap;

import org.junit.Test;

public class lib1_compute
{
	@Test
	public void test()
	{
		
	}
	public static int contains(section sec, String s)
	{
		for (int x = 1; x <= sec.cnt; x++)
		{
			if (sec.st[x].equals(s))
				return x;
		}
		return 0;
	}

	public static section reset(section sec)
	{
		section s = new section();
		s.cnt = 0;
		s.flag = sec.flag;
		s.val = sec.val;
		for (int x = 1; x <= sec.cnt; x++)
		{
			int count = contains(s, sec.st[x]);
			if (count != 0)
				s.pow[count] = s.pow[count] + 1;
			else
			{
				s.cnt++;
				s.st[s.cnt] = sec.st[x];
				s.pow[s.cnt] = 1;
			}

		}
		return s;
	}

	public static void simplify(String s, section[] sec)
	{
		
		String[] vars = s.split(",");
		HashMap<String, Double> hm = new HashMap();
		for (String var : vars)
		{
			String[] lines = var.split("=");
			hm.put(lines[0], Double.valueOf(lines[1]));
		}
		String out = "";
		double out_num = 0;
		boolean f1 = false;// false代表 并非全部变量给出了初值
		int i = 0;
		while (sec[++i].val >= 1)
		{
			section se = sec[i];
			String element = "";
			int val = se.val;
			for (int j = 1; j <= se.cnt; j++)
			{
				String var = se.st[j];
				if (hm.containsKey(var))
				{
					val *= hm.get(var);

				}
				else
				{
					element += ("*" + var);
					f1 = true;
				}
			}
			if (i == 1)
				out = out + val + element;
			else
				out = out + "+" + val + element;

			// 判断se后的符号，然后决定out_num是+=还是-=
			// out=out+se.*+element.substring(0, element.length()-1);
			// *代表每个se后的加减号
		}

		if (f1 == true)// 有问题需要解决
		{
			System.out.println("输出结果:" + out);
		}
		else
		{
			String[] lines = out.split("\\+");
			double result = 0;
			for (String line : lines)
			{
				result += Double.valueOf(line);
			}
			System.out.println("输出结果:" + result);
		}

		return;
	}

	public static void d(String v, section[] sec)
	{
		int i = 0;
		while (sec[++i].val >= 1)
		{
			sec[i] = reset(sec[i]);
		}
		String out = "";
		i = 0;

		while (sec[++i].cnt >= 1)
		{
			section se = sec[i];
			String element = "";
			int val = se.val;
			for (int j = 1; j <= se.cnt; j++)
			{
				if (se.st[j].equals(v))
				{
					val = val * (se.pow[j]);
					if (se.pow[j] == 1)
					{
					}
					else
					{
						for (int k = 1; k < se.pow[j]; k++)
						{
							element = element + "*" + se.st[j];
						}
					}
				}
				else
				{
					for (int k = 1; k <= se.pow[j]; k++)
					{
						element = element + "*" + se.st[j];
					}
				}

				// 判断se后的符号，然后决定out_num是+=还是-=
				// out=out+se.*+element.substring(0, element.length()-1);
				// *代表每个se后的加减号
			}
			if (i == 1)
				out = out + val + element;
			else
				out = out + "+" + val + element;
		}
		System.out.println("输出求导:" + out);
	}

}
