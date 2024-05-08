package com.example.tcc.dados;

import java.util.ArrayList;
import java.util.List;

    public class Caracteristicas {

        private ArrayList<CaracterTest> vectorDados;

        public Caracteristicas(ArrayList<CaracterTest> vectorDados) {
            this.vectorDados = vectorDados;
            mostrarDadosBrutos();
            double[] features = extrairFeatures();
            mostrar(features);
        }

        public Caracteristicas(){

        }

        public void mostrarDadosBrutos() {
            System.out.println("\n\n====Dados Brutos===\n\n");
            for (CaracterTest data : vectorDados) {
                System.out.print(data.getCaracter() + "\t" + data.getDownTime() + "\t" + data.getUpTime() + "\n");
            }
            System.out.println("\n\n");
        }

        public double[] extrairFeatures() {
            List<Double> duracoes = new ArrayList<>();
            List<Double> PPs = new ArrayList<>();
            List<Double> SSs = new ArrayList<>();
            List<Double> PSs = new ArrayList<>();

            int tamanho = vectorDados.size();
            int inicio = 0;
            int fim = 9;

            while (inicio < tamanho) {
                List<CaracterTest> linhasDeDados = vectorDados.subList(inicio, fim);

                for (int i = 0; i < linhasDeDados.size(); i++) {
                    CaracterTest linha = linhasDeDados.get(i);
                    long Tp1 = linha.getDownTime();
                    long Ts1 = linha.getUpTime();

                    if (i < linhasDeDados.size() - 1) {
                        CaracterTest proximaLinha = linhasDeDados.get(i + 1);
                        long Tp2 = proximaLinha.getDownTime();
                        long Ts2 = proximaLinha.getUpTime();

                        double duracao = calculaDuracao(Tp1, Ts1);
                        double PP = calculaPP(Tp1, Tp2);
                        double SS = calculaSS(Ts1, Ts2);
                        double PS = calculaPS(Tp1, Ts2);

                        duracoes.add(duracao);
                        PPs.add(PP);
                        SSs.add(SS);
                        PSs.add(PS);
                    }
                }

                inicio += 9;
                fim += 9;
            }

            double media_duracao = calcularMedia(duracoes);
            double media_PP = calcularMedia(PPs);
            double media_SS = calcularMedia(SSs);
            double media_PS = calcularMedia(PSs);

            double[] features = new double[36];
            int idx = 0;
            for (int i = 0; i < duracoes.size(); i++) {
                features[idx++] = duracoes.get(i);
                features[idx++] = PPs.get(i);
                features[idx++] = SSs.get(i);
                features[idx++] = PSs.get(i);
            }
            features[idx++] = media_duracao;
            features[idx++] = media_PP;
            features[idx++] = media_SS;
            features[idx] = media_PS;

            return features;
        }

        public static double calculaDuracao(long linha1, long linha2) {
            return (linha2 - linha1) / 1000.0; // Convertendo de milissegundos para segundos
        }

        public static double calculaPP(long linha1, long linha2) {
            return (linha2 - linha1) / 1000.0; // Convertendo de milissegundos para segundos
        }

        public static double calculaSS(long linha1, long linha2) {
            return (linha2 - linha1) / 1000.0; // Convertendo de milissegundos para segundos
        }

        public static double calculaPS(long linha1, long linha2) {
            return (linha2 - linha1) / 1000.0; // Convertendo de milissegundos para segundos
        }

        public static double calcularMedia(List<Double> lista) {
            double soma = 0;
            for (double valor : lista) {
                soma += valor;
            }
            return soma / lista.size();
        }

        public void mostrar(double[] features) {
            // Imprimir os dados
            System.out.println("====Fearures=====");
            System.out.println("\n\n");
            System.out.println("Duracao.v\tPP.v_i\tSS.v_i\tPS.v_i\tDuracao.i\tPP.i_d\tSS.i_d\tPS.i_d\tDuracao.d\tPP.d_a\tSS.d_a\tPS.d_a\tDuracao.a\tPP.a_ponto\tSS.a_ponto\tPS.a_ponto\tDuracao.ponto\tPP.ponto_n\tSS.ponto_n\tPS.ponto_n\tDuracao.n\tPP.n_o\tSS.n_o\tPS.n_o\tDuracao.o\tPP.o_v\tSS.o_v\tPS.o_v\tDuracao.v2\tPP.v_a\tSS.v_a\tPS.v_a\tMedia_Duracao\tMedia_PP\tMedia_PS\tMedia_SS");
            int idx = 0;
            while (idx < features.length) {
                for (int i = 0; i < 36 && idx < features.length; i += 4) {
                    System.out.print(features[idx++] + "\t" + features[idx++] + "\t" + features[idx++] + "\t" + features[idx++] + "\t");
                }
                System.out.println("\n\n");
            }
        }
    }


