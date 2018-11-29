% bar plot

EnK = 82.1
ToR = 10.3
TreR = 2.6
FireR = 2.6
FemR = 2.6

figure
bK1 = [EnK;ToR;TreR;FireR;FemR]
cK1K = categorical({'Forkert lægemiddel', 'Forkert formulering', 'Forkert dosis', 'Forkert administrationsvej', 'Udeladelsese af dosis'});
cK1K = reordercats(cK1K,{'Forkert lægemiddel', 'Forkert formulering', 'Forkert dosis', 'Forkert administrationsvej', 'Udeladelsese af dosis'});
bar(cK1K,bK1)
%title('Medicineringsfejl ved generisk substitution')
ylabel('Antal medicineringsfejl (%)')
ylim([0 100])
%labels = arrayfun(@(value) num2str(value,'%2.1f'),bK1,'UniformOutput',false);
   % text(cK1K,bK1,labels,'HorizontalAlignment','center','VerticalAlignment','bottom') 
set(gca,'fontsize',20)


EnKK = 33.8
ToKK = 29.6
TreKK = 25.4
FireKK = 4.2
FemKK = 4.2
SeksKK = 2.8

figure
bKK1 = [EnKK;ToKK;TreKK;FireKK;FemKK;SeksKK]
cKK1K = categorical({'Lignende og/eller svært navn', 'Tung arbejdsbyrde', 'Sjusk/fravær af dobbelttjek', 'Utilstrækkelig journalførring/ordination', 'Usikkerhed', 'Andet'});
cKK1K = reordercats(cKK1K,{'Lignende og/eller svært navn', 'Tung arbejdsbyrde', 'Sjusk/fravær af dobbelttjek', 'Utilstrækkelig journalførring/ordination', 'Usikkerhed', 'Andet'});
bar(cKK1K,bKK1)
%title('Årsager til medicineringsfejl ved generisk substitution')
ylabel('Antal årsager (%)')
ylim([0 35])
%labels = arrayfun(@(value) num2str(value,'%2.1f'),bKK1,'UniformOutput',false);
    %text(cKK1K,bKK1,labels,'HorizontalAlignment','center','VerticalAlignment','bottom') 
set(gca,'fontsize',20)

%%
figure
ny = [EnKK;ToKK;TreKK;FireKK;FemKK;SeksKK] 
barh(sum(ny), 'y')
hold on
barh(ny(1), 'b')
hold on
barh(ny(2), 'r')
hold on
barh(ny(3), 'r')
hold on
barh(ny(4), 'r')
hold on
barh(ny(5), 'r')
hold on
barh(ny(6), 'r')
title('Årsager til medicineringsfejl ved generisk substitution')
xlabel('Antal årsager (%)')
set(gca,'fontsize',20)



%% Kontraktskift
% 
% EnKD = 27
% EnKP = 6
% ToKD= 6
% ToKP= 2
% TreKD = 4
% TreKP = 2
% FireKD = 3
% FireKP = 2
% FemKD = 2
% FemKP = 1
% SeksKD = 1
% SeksKP = 0
% 
% figure
% bK1 = [EnKD, EnKP; ToKD, ToKP; TreKD, TreKP; FireKD, FireKP; FemKD, FemKP; SeksKD, SeksKP]
% cK1K = categorical({'Look-a-like', 'Forkert lægemiddel ordineret', 'Dobbeltordination', 'Sound-a-like', 'Forkert dosis', 'Forsinket indgift'});
% cK1K = reordercats(cK1K,{'Look-a-like', 'Forkert lægemiddel ordineret', 'Dobbeltordination', 'Sound-a-like', 'Forkert dosis', 'Forsinket indgift'});
% bar(cK1K,bK1)
% b = bar(bK1)
% title('Typer af utilsigtede hændelser relateret til kontraktskift')
% ylabel('Antal utilsigtede hændelser (%)')
% ylim([0 30])
% % labels = arrayfun(@(value) num2str(value,'%2.1f'),bK1,'UniformOutput',false);
% % text(cK1,bK1,labels,'HorizontalAlignment','center','VerticalAlignment','bottom') 
% width = b.BarWidth;
% for i=1:length(bK1(:, 1))
%     row = bK1(i, :);
%     % 0.5 is approximate net width of white spacings per group
%     offset = ((width + 0.5) / length(row)) / 2;
%     cK1K = linspace(i-offset, i+offset, length(row));
%     text(cK1K,row,num2str(row'),'vert','bottom','horiz','center');
% end
% set(gca,'fontsize',20)
% 
% %% Restordre
% 
% EnR1 = 28
% ToR1 = 47
% TreR1 = 3
% FireR1 = 3
% FemR1 = 0
% 
% EnR1a = 13
% ToR1a = 67
% TreR1a = 7
% FireR1a = 0
% FemR1a = 13
% 
% EnR2 = 54,9
% ToR2 = 36
% TreR2 = 36,4
% FireR2 = 0
% FemR2 = 0
% 
% figure
% bK1R = [EnR1,EnR1a, EnR2;ToR1,ToR1a, ToR2;TreR1, TreR1a, TreR2;FireR1, FireR1a, FireR2;FemR1,FemR1a,FemR2]
% %cK1K = categorical({'Forkert lægemiddel', 'Forkert dosis', 'Forsinket eller udeladt dispensering og administrering', 'Manglende information i forhold til dispensering af erstatningslægemiddel', 'Dobbeltdosering'});
% %cK1K = reordercats(cK1K,{'Forkert lægemiddel', 'Forkert dosis', 'Forsinket eller udeladt dispensering og administrering', 'Manglende information i forhold til dispensering af erstatningslægemiddel', 'Dobbeltdosering'});
% cK1K = categorical({'Forkert lægemiddel', 'Forkert dosis', 'Forsinket eller udeladt', 'Manglende information', 'Dobbeltdosering'});
% cK1K = reordercats(cK1K,{'Forkert lægemiddel', 'Forkert dosis', 'Forsinket eller udeladt', 'Manglende information', 'Dobbeltdosering'});
% bar(cK1K,bK1R)
% %bR = bar(bK1)
% title('Typer af utilsigtede hændelser ved restordre')
% ylabel('Antal utilsigtede hændelser (%)')
% legend('McBride et al, nærhændelser (n = 39)', 'McBride et al, hændelser (n = 15)', 'McLaughlin et al, hændelser (n = 236)')
% ylim([0 70])
% % width = bR.BarWidth;
% % for i=1:length(bR(:, 1))
% %     row = bR(i, :);
% %     % 0.5 is approximate net width of white spacings per group
% %     offset = ((width + 0.5) / length(row)) / 2;
% %     cK1K = linspace(i-offset, i+offset, length(row));
% %     text(cK1K,row,num2str(row'),'vert','bottom','horiz','center');
% % end
% set(gca,'fontsize',20)


