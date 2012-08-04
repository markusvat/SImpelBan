package com.markusvat.SimpelBan;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
import Commands.markusvat.CommandKICK;
public class Main extends JavaPlugin{
	public void onEnable(){
		System.out.println("Enabel");
		getCommand("kick").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
				// TODO Auto-generated method stub
				String s ="[Server] du wurdest gekickt [Grund]:";
				if (arg.length>=2){
				for (int i=1;i!=arg.length;i++){
					s=s+" "+arg[i];
				}
				}else if(arg.length==1){
					s="[Server] du wurdest gekickt";
				}else{
					sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Bitte Spieler namen zum kicken eingeben");
				  return true;	
				}
				Player kick;
				try{kick=Bukkit.getPlayer(arg[0]);}catch(Exception e){sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Spieler ist nicht online");return true;}
				if (kick!=null){
				return new CommandKICK(kick, s).run();
				}else{
					sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Spieler "+"Zu viele Argumente  /"+cmdlabel);
					return true;
				}
			}
		});
		getCommand("ban").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
				if (arg.length==0){
					sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Spieler namen eingeben (exakt ohne groﬂ und klein schreibung), alle Spieler mit teilen des namen des gebannten werden gekickt, aber nur der gebannte selber wird gebannt");
					return true;
				}else if(arg.length==1){
					List<String> a = getConfig().getStringList("Player");
					boolean banned = false;
					for(String s : a){
						if (s.split("%20")[0].equalsIgnoreCase(arg[0])){
							banned=true;
						}
					}
					
					if(banned==false){
						a.add(arg[0]+"%20"+"0"+"%20"+"standart");
						getConfig().set("Player", a);
						getConfig().options().copyDefaults(true);
						saveConfig();
						Bukkit.broadcastMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.RED+arg[0]+" wurde vom Server gebannt");
						Player ban;
						try {ban=Bukkit.getPlayer(arg[0]);} catch (Exception e) {return true;}
						if (ban != null&&ban.getName().equalsIgnoreCase(arg[0])){
						return new CommandKICK(ban.getPlayer(), "[SERVER]: Du wurdest gebannt").run();
						}else{
							sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Spieler ist nicht online wurde aber gebannt");
						}
					}else{
							sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+arg[0]+" wurde schon gebannt");
					}
				}else if(arg.length==2){
					List<String> a = getConfig().getStringList("Player");
					boolean banned = false;
					for(String s3 : a){
						if (s3.split("%20")[0].equalsIgnoreCase(arg[0])){
							banned=true;
						}
					}
					if (banned==false){
						int bannt = 0;
						boolean timeb=true;
						long time = 0;
						try{bannt=Integer.parseInt(arg[1])*60000;timeb=true;}catch(Exception e){timeb=false;}
						if(timeb){
					time =System.currentTimeMillis()+bannt;
					a.add(arg[0]+"%20"+time+"%20"+"standart");
						}else{
					a.add(arg[0]+"%20"+"0"+"%20"+arg[1]);
						}
					getConfig().set("Player", a);
					getConfig().options().copyDefaults(true);
					saveConfig();
					Player ban;
					try {ban=Bukkit.getPlayer(arg[0]);} catch (Exception e) {return true;}
					if (ban != null&&ban.getName().equalsIgnoreCase(arg[0])){
					return new CommandKICK(ban.getPlayer(), "[Server]: Du wurdest gebannt").run();
					}else{
						if(timeb){
							sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Spieler ist nicht online wurde aber gebannt bis :"+time);
						}else{
							sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Spieler ist nicht online wurde aber Grund:"+arg[0]);
						}
						}
				}else{
					sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+arg[0]+" wurde schon gebannt");
				}
					}else if(arg.length>=3){
						
						String s="";
						long time = 0;
						List<String> a = getConfig().getStringList("Player");
						boolean banned = false;
						for(String s3 : a){
							if (s3.split("%20")[0].equalsIgnoreCase(arg[0])){
								banned=true;
							}
						}
						if (banned==false){
							int bannt = 0;
							boolean timeb=true;
							try{bannt=Integer.parseInt(arg[1])*60000;timeb=true;}catch(Exception e){timeb=false;}
						
					if(timeb){
						time =System.currentTimeMillis()+bannt;
						
						for (int i=2;i!=arg.length;i++){
							s=s+arg[i];
						}
						a.add(arg[0]+"%20"+time+"%20"+s);
							}else{
								
						for (int i=1;i!=arg.length;i++){
						s=s+arg[i];
						}
						a.add(arg[0]+"%20"+"0"+"%20"+s);
							}
						getConfig().set("Player", a);
						getConfig().options().copyDefaults(true);
						saveConfig();
						
						Player ban;
						try {ban=Bukkit.getPlayer(arg[0]);} catch (Exception e) {return true;}
						if (ban != null&&ban.getName().equalsIgnoreCase(arg[0])){
						return new CommandKICK(ban.getPlayer(), s).run();
						}else{
							String s1="";
							for (int i=1;i!=arg.length;i++){
								s1=s1+arg[i];
							}
							sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Spieler "+arg[0]+" ist nicht online wurde aber gebannt Grund: "+s+ " bis "+time);
						}
					}else{
						sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+arg[0]+" wurde schon gebannt");
					}
					
				}
					
				return true;
			}		
		});
		
	
		
		getCommand("unban").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
				if (arg.length==0){
					sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Spieler namen eingeben");
					return true;
				}else if(arg.length==1){
					boolean unban=false;
					List<String> a = getConfig().getStringList("Player");
					try{
					for (int i = 0; i!=a.size();i++){			
						String s = a.get(i).split("%20")[0];
						if (s.equalsIgnoreCase(arg[0])){
							a.remove(i);
							Bukkit.broadcastMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.GREEN+arg[0]+" wurde entbannt");
							unban=true;
							break;
						}
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					if (unban==false){
						sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Speiler "+arg[0]+" war nicht gebannt");
					}
					getConfig().set("Player", a);
					getConfig().options().copyDefaults(true);
					saveConfig();
				}else if(arg.length>=2){
				sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Spieler "+"Zu viele Argumente  /"+cmdlabel);
				}
				return true;
			}
		});
		
		
		getCommand("c").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
			for (int i=0;i<30;i++){
				sender.sendMessage("");
			}
			return true;
			}
		});
		getCommand("getbanned").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
				if (arg.length==0){
					List<String> a = getConfig().getStringList("Player");
					if (a.size()==0){
						sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"keine Spieler wurden gebannt");
					}else {
						sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Von diesem Server gebannte Spieler");
						for (String s : a){
							if (s.split("%20")[1].toString().equalsIgnoreCase("standart")){
								sender.sendMessage(s.split("%20")[0]);
							}else{
								sender.sendMessage(s.split("%20")[0]+" Grund: "+s.split("%20")[1]);
							}
						}
					}
				}else{
					sender.sendMessage(ChatColor.WHITE+"["+ChatColor.GREEN+"MultiBan"+ChatColor.WHITE+"]: "+ChatColor.BLUE+"Spieler "+"Zu viele Argumente  /"+cmdlabel);
				}
				return true;
			}
		});
	getServer().getPluginManager().registerEvents(new Listener() { @EventHandler
		public void onPlayerLoginEvent(PlayerLoginEvent p) {
		String name=p.getPlayer().getName();
		List<String> a = getConfig().getStringList("Player");
		for (int i = 0;i!=a.size();i++){
		String banned=a.get(i).split("%20")[0];
			if (banned.equalsIgnoreCase(name)){
				//a.remove(i);
				if (a.get(i).split("%20")[2].equalsIgnoreCase("standart")){
					if(Long.parseLong(a.get(i).split("%20")[1])==0){
						
						p.setKickMessage("[Server]: du bist gebannt checks endlich");
						p.getPlayer().kickPlayer("[Server]: du bist gebannt checks endlich");
						p.setResult(Result.KICK_BANNED);
					}else{
						if(Long.parseLong(a.get(i).split("%20")[1])>System.currentTimeMillis()){
						long n = System.currentTimeMillis();
						long rest=Long.parseLong(a.get(i).split("%20")[1])-n;
						p.setKickMessage("[Server]: du bist noch "+((rest/60000)+1)+" Minuten gebannt");
						p.getPlayer().kickPlayer("[Server]: du bist gebannt checks endlich");
						p.setResult(Result.KICK_BANNED);
						}else{
							if(a.size()==1){
							getConfig().set("Player", "");
							getConfig().options().copyDefaults(true);
							saveConfig();
							}else{
								a.remove(i);
								getConfig().set("Player", "");
								getConfig().options().copyDefaults(true);
								saveConfig();
							}
						}
					}
				}else{
					if(a.get(i).split("%20")[1]=="0"){
						p.setKickMessage("[Server]: du bist gebannt [Grund]:"+a.get(i).split("%20")[2]);
						p.getPlayer().kickPlayer("[Server]: du bist gebannt [Grund]:"+a.get(i).split("%20")[1]);
						p.setResult(Result.KICK_BANNED);
					}else{
						if(Long.parseLong(a.get(i).split("%20")[1])>System.currentTimeMillis()){
						long n = System.currentTimeMillis();
						long rest=Long.parseLong(a.get(i).split("%20")[1])-n;
						p.setKickMessage("[Server]: du bist noch"+((rest/60000)+1)+" Minuten gebannt [Grund]:"+a.get(i).split("%20")[2]+" bis "+((rest/60000)+1)+" Minuten");
						p.getPlayer().kickPlayer("[Server]: du bist noch gebannt [Grund]:"+a.get(i).split("%20")[1]);
						p.setResult(Result.KICK_BANNED);
						}else{
							if(a.size()==1){
								getConfig().set("Player", "");
								getConfig().options().copyDefaults(true);
								saveConfig();
								}else{
									a.remove(i);
									getConfig().set("Player", "");
									getConfig().options().copyDefaults(true);
									saveConfig();
								}
						}
					}
				}
			}
		}
	}}, this);
}
}
