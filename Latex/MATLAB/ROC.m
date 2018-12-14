

XL = [0, 0, 0.045, 0.045, 0.045, 0.182, 0.273, 0.364, 0.455, 0.591, 0.636, 0.909, 1];
YL = [0, 0.1, 0.2, 0.3, 0.5, 0.5, 0.6, 0.7, 0.8, 0.8 , 1, 1, 1];

X_posL = 0.045;
Y_posL = 0.5;

X_posLL = 0.455;
Y_posLL=0.8;

DiaX = [0 1];
DiaY = [0 1];

X = [0, 0, 0, 0, 0, 0.143, 0.238, 0.286, 0.429, 0.524, 0.619, 0.905, 1];
Y = [0, 0.091, 0.273, 0.364, 0.545, 0.545, 0.636, 0.818, 0.818, 0.909 , 1, 1, 1];

X_pos = 0.286;
Y_pos = 0.818;


figure
subplot(1,2,1)
plot(XL,YL)
line(DiaX, DiaY, 'Color','red')
hold on
plot(X_posLL,Y_posLL,'g*')
xlim([0 1])
ylim([0 1])
set(gca, 'XTick', (0:0.1:1))
set(gca, 'YTick', (0:0.1:1))
grid on
set(gca,'fontsize',20)
ylabel('Sensitivitet')
xlabel('1-Specificitet')
hold on
subplot(1,2,2)
plot(X, Y)
line(DiaX, DiaY, 'Color','red')
hold on
plot(X_pos,Y_pos,'g*')
xlim([0 1])
ylim([0 1])
set(gca, 'XTick', (0:0.1:1))
set(gca, 'YTick', (0:0.1:1))
grid on
set(gca,'fontsize',20)
ylabel('Sensitivitet')
xlabel('1-Specificitet')