SubagentX Python
================
1 - Instalar a biblioteca "netsnmp-python" : https://github.com/pief/python-netsnmpagent/
2 - Utilizar a arquivo de configuração fornecido (snmpd.conf)
3 - Iniciar o agente snmpd como master
4 - Rodar o subagent como administrador

Mibs
====
 - BASE-MIB.txt    : Mib que se liga diretamente ao subárvores da UFRGS (enterprise 12619), criando um nó chamado gerenciaUFRGS.
 - MINHAMIB-MIB.txt: Mib na qual será desenvolvida o trabalho. Cria um nó em gerenciaUFRGS chamado minhaMIB, abaixo desse nó todos os outros nós serão conectados.
 
Exemplo de consulta
====================
 - snmpget -v 2c -c public localhost MINHAMIB-MIB::infoAlunos.0
