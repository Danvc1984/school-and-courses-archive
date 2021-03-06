function Y = nolineal(Img,Tam,Tipo)
I=imread(Img);
R=I(:,:,1);
ImgO=R;
figure('Name','Original','NumberTitle','off')
imshow(R);
[M,N]=size(ImgO);
ImgC=ImgO;

inc = (Tam-1)/2;

%Construir imagen copia con borde
for i=1:(M+(inc*2))
    for j=1:(N+(inc*2))
        if i>(inc) && i<=(M+inc) && j>(inc) && j<=(N+inc)
            ImgC(i,j)=ImgO((i-(inc)),(j-(inc)));
        else
            ImgC(i,j) = 0;
        end
    end
end

%Imagen resultante
ImgR=ImgO;
switch Tipo
    case 'm'
        for x=1+inc:M+inc
            for y = 1+inc:N+inc
                suma=255;
                for i=-inc:+inc
                    for j=-inc:+inc
                        valor=ImgC(x+i,y+j);
                        if(valor<suma)
                            suma=valor;
                        end
                    end
                end
                ImgR(x-inc,y-inc)=suma;
            end
        end
    case 'M'
        for x=1+inc:M+inc
            for y = 1+inc:N+inc
                suma=0;
                for i=-inc:+inc
                    for j=-inc:+inc
                        valor=ImgC(x+i,y+j);
                        if(valor>suma)
                            suma=valor;
                        end
                    end
                end
                ImgR(x-inc,y-inc)=suma;
            end
        end
    case 'med'
        for x=1+inc:M+inc
            for y = 1+inc:N+inc
                suma=zeros(1,Tam*Tam);
                cont=1;
                for i=-inc:+inc
                    for j=-inc:+inc
                        valor=ImgC(x+i,y+j);
                        suma(1,cont)=valor;
                        cont=cont+1;
                    end
                end
                medio=median(suma(1,:));
                ImgR(x-inc,y-inc)=medio;
            end
        end
end

% figure('Name','Final','NumberTitle','off')
%imshow(ImgR);
Y=ImgR;
end 