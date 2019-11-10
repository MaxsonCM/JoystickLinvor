package com.webnode.maxsoncm.joysticklinvor;

/**
 * Created by Maxson on 03/02/2016.
 */
public class Obj_Layout {

        private long lay_codigo;
        private long lay_altura;
        private long lay_largura;
        private String lay_nome;
        private String lay_descricao;
        private byte[] lay_screen;
        private byte[] lay_banner;

        public long getCodigo() {
            return lay_codigo;
        }
        public void setCodigo(long lay_codigo) {
            this.lay_codigo = lay_codigo;
        }
        public long getAltura() {
            return lay_altura;
        }
        public void setAltura(long lay_altura) {
            this.lay_altura = lay_altura;
        }
        public long getLargura() {
            return lay_largura;
        }
        public void setLargura(long lay_largura) {
            this.lay_largura = lay_largura;
        }
        public String getNome() {
            return lay_nome;
        }
        public void setNome(String lay_nome) {
            this.lay_nome = lay_nome;
        }
        public String getDescricao() {
            return lay_descricao;
        }
        public void setDescricao(String lay_descricao) {
            this.lay_descricao = lay_descricao;
        }
        public byte[] getImagem() {
            return lay_screen;
        }
        public void setImagem(byte[] lay_screen) {
            this.lay_screen = lay_screen;
        }
        public byte[] getBanner() {
        return lay_banner;
    }
        public void getBanner(byte[] lay_banner) {
        this.lay_banner = lay_banner;
    }
        public void setBanner(byte[] lay_screen) {
            this.lay_screen = lay_screen;
        }
}
