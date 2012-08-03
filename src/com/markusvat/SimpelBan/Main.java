package com.markusvat.SimpelBan;

import java.util.List;

import org.bukkit.Bukkit;
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
					sender.sendMessage("Bitte Spieler namen zum kicken eingeben");
				  return true;	
				}
				return new CommandKICK((Player) sender, s).run();
			}
			
		});
		getCommand("ban").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
				if (arg.length==0){
					sender.sendMessage("Spieler namen eingeben");
					return true;
				}else if(arg.length==1){
					List<String> a = getConfig().getStringList("Player");
					a.add(arg[0]+"%20"+"standart");
					
					
					getConfig().set("Player", a);
					getConfig().options().copyDefaults(true);
					saveConfig();
					Player ban;
					try {ban=Bukkit.getPlayer(arg[0]);} catch (Exception e) {return true;}
					
					if (ban != null){
					return new CommandKICK(ban.getPlayer(), "[SERVER]: Du wurdest gebannt").run();
					}else{
						sender.sendMessage("Spieler nicht gefunden");
					}
				}else if(arg.length>=2){
					
					String s="Du wurdest gebannt [Grund]:";
					List<String> a = getConfig().getStringList("Player");
					a.add(arg[0]+"%20"+s);
					
					getConfig().set("Player", a);
					getConfig().options().copyDefaults(true);
					saveConfig();
					Player ban;
					try {ban=Bukkit.getPlayer(arg[0]);} catch (Exception e) {return true;}
						if (ban != null){
							return new CommandKICK(ban.getPlayer(), "[SERVER]: Du wurdest gebannt [Grund]:"+s).run();
						}else{
							sender.sendMessage("Spieler nicht gefunden");
						}
				}
				return true;
			}
			
			
			
		});
		
		
		getCommand("unban").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd,	String cmdlabel, String[] arg) {
				if (arg.length==0){
					sender.sendMessage("Spieler namen eingeben");
					return true;
				}else if(arg.length==1){
					boolean unban=false;
					List<String> a = getConfig().getStringList("Player");
					try{
					for (int i = 0; i!=a.size();i++){
						
						if (a.get(i).split("%20").length>=1){ //hier soll der fehler sein
						String s = a.get(i).split("%20")[0];
						System.out.println(s);
						System.out.print(arg[0]);
						if (s.equalsIgnoreCase(arg[0])){
							a.remove(i);
							Bukkit.broadcastMessage(s+"wurde entbannt");
							unban=true;
						}
						}
						}
					}catch(ArrayIndexOutOfBoundsException e){}
					if (unban==false){
						sender.sendMessage("Speiler war nicht gebannt");
					}
					getConfig().set("Player", a);
					getConfig().options().copyDefaults(true);
					saveConfig();
				}else if(arg.length>=2){
				sender.sendMessage("Spieler nicht gefunden");
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
						
						
						sender.sendMessage("keine Spieler wurden gebannt");
					
					}else {
						sender.sendMessage("Von diesem Server gebannte Spieler");
						for (String s : a){
							sender.sendMessage(s.split("%20")[0]);
						}
					}
				}else{
					sender.sendMessage("zu viele argumente");
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
				if (a.get(i).split("%20")[1].equalsIgnoreCase("standart")){
					System.out.println("test");
					
					p.setKickMessage("[Server]: du bist gebannt checks endlich");
					p.getPlayer().kickPlayer("[Server]: du bist gebannt checks endlich");
					p.setResult(Result.KICK_BANNED);
					
					
				}else{
					
					p.setKickMessage("[Server]: du bist gebannt [Grund]:"+a.get(i).split("%20")[1]);
					p.getPlayer().kickPlayer("[Server]: du bist gebannt [Grund]:"+a.get(i).split("%20")[1]);
					p.setResult(Result.KICK_BANNED);
					
				}
			}
		}
	}}, this);
	
}
}
